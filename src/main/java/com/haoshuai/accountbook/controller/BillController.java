package com.haoshuai.accountbook.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.haoshuai.accountbook.entity.Bill;
import com.haoshuai.accountbook.entity.User;
import com.haoshuai.accountbook.entity.model.BillLineChartData;
import com.haoshuai.accountbook.entity.model.BillModel;
import com.haoshuai.accountbook.entity.model.BillPieData;
import com.haoshuai.accountbook.service.IBillService;
import com.haoshuai.accountbook.service.IUserService;
import com.haoshuai.accountbook.utils.Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 账单
 * 前端控制器
 * </p>
 *
 * @author TangHaoShuai
 * @since 2022-04-19
 */
@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private IBillService iBillService;

    @Autowired
    private IUserService iUserService;


    /**
     * 账目饼图数据
     *
     * @param bill
     * @return
     */
    @PostMapping("getBillPieData")
    public BillPieData getBillPieData(@RequestBody Bill bill) {
        if (!Util.isStringNull(bill.getAccountBookId()) && !Util.isStringNull(bill.getBillType())) return null;
        BillPieData billPieData = new BillPieData();
        String spending_type[] = new String[]{"食品饮料", "衣服饰品", "交通通讯", "寝室费用", "学习进修", "休闲娱乐", "恋爱基金", "医疗保障", "其他杂项"}; //支出类型
        String income_type[] = new String[]{"职业收入", "其他收入"}; //收入类型
        if (bill.getBillType().equals("支出")) {
            List<BillPieData.SeriesBeanA> seriesBeanAS = new ArrayList<>();
            BillPieData.SeriesBeanA seriesBeanA = new BillPieData.SeriesBeanA();
            List<BillPieData.SeriesBeanA.DataBean> dataBeans = new ArrayList<>();
            for (String s : spending_type) {

                QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
                billQueryWrapper.eq("account_book_id", bill.getAccountBookId());
                billQueryWrapper.eq("bill_type", "支出");
                billQueryWrapper.eq("consume_type", s);
                List<Bill> billList = iBillService.list(billQueryWrapper);
                if (billList.size() > 0) {
                    BillPieData.SeriesBeanA.DataBean dataBean = new BillPieData.SeriesBeanA.DataBean();
                    double temp = 0.0;
                    for (Bill b : billList) {
                        temp += b.getMoney();
                    }
                    dataBean.setName(s);
                    dataBean.setValue(temp);
                    dataBeans.add(dataBean);
                }
            }
            seriesBeanA.setData(dataBeans);
            seriesBeanAS.add(seriesBeanA);
            billPieData.setSeries(seriesBeanAS);
        } else if (bill.getBillType().equals("收入")) {
            List<BillPieData.SeriesBeanA> seriesBeanAS = new ArrayList<>();
            BillPieData.SeriesBeanA seriesBeanA = new BillPieData.SeriesBeanA();
            List<BillPieData.SeriesBeanA.DataBean> dataBeans = new ArrayList<>();
            for (String s : income_type) {
                QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
                billQueryWrapper.eq("account_book_id", bill.getAccountBookId());
                billQueryWrapper.eq("bill_type", "收入");
                billQueryWrapper.eq("consume_type", s);
                List<Bill> billList = iBillService.list(billQueryWrapper);
                if (billList.size() > 0) {
                    BillPieData.SeriesBeanA.DataBean dataBean = new BillPieData.SeriesBeanA.DataBean();
                    double temp = 0.0;
                    for (Bill b : billList) {
                        temp += b.getMoney();
                    }
                    dataBean.setName(s);
                    dataBean.setValue(temp);
                    dataBeans.add(dataBean);

                }
            }
            seriesBeanA.setData(dataBeans);
            seriesBeanAS.add(seriesBeanA);
            billPieData.setSeries(seriesBeanAS);
        }
        return billPieData;
    }

    /**
     * 账目折线图数据
     *
     * @param bill
     * @return
     */
    @PostMapping("getBillLineChartData")
    public BillLineChartData getBillLineChartData(@RequestBody Bill bill) {
        if (Util.isStringNull(bill.getAccountBookId())) {
            BillLineChartData billLineChartData = new BillLineChartData();

//            Collections.reverse(billList);//逆转
            BillLineChartData.SeriesBean income = new BillLineChartData.SeriesBean(); //收入
            BillLineChartData.SeriesBean expend = new BillLineChartData.SeriesBean(); //支出
            List<String> categories = new ArrayList<>();  //日期
            List<Double> incomeData = new ArrayList<>();  //收入数据
            List<Double> expendData = new ArrayList<>();  //支出数据

            SimpleDateFormat df = new SimpleDateFormat("yyyy年M月d日");//设置日期格式
            for (int i = 0; i < 7; i++) {
                Calendar calendar = Calendar.getInstance(); //创建Calendar 的实例
                calendar.add(Calendar.DAY_OF_MONTH, (-6 + i)); //当前时间减去一天，即一天前的时间
                categories.add(df.format(calendar.getTime()));
            }

            //获取近七天每天支出金额
            for (String s : categories) {
                QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("account_book_id", bill.getAccountBookId());
                queryWrapper.eq("bill_type", "支出");
                queryWrapper.eq("date", s);
                List<Bill> billList = iBillService.list(queryWrapper);
                double temp = 0.0;
                for (Bill b : billList) {
                    temp += b.getMoney();
                }
                expendData.add(temp);
            }
            //获取近七天每天收入金额
            for (String s : categories) {
                QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("account_book_id", bill.getAccountBookId());
                queryWrapper.eq("bill_type", "收入");
                queryWrapper.eq("date", s);
                List<Bill> billList = iBillService.list(queryWrapper);
                double temp = 0.0;
                for (Bill b : billList) {
                    temp += b.getMoney();
                }
                incomeData.add(temp);
            }

            for (int i = 0; i < categories.size(); i++) {
                String temp = categories.get(i).substring(5);
                categories.set(i, temp);
            }

            income.setName("收入");
            expend.setName("支出");
            income.setData(incomeData);
            expend.setData(expendData);
            List<BillLineChartData.SeriesBean> seriesBeanList = new ArrayList<>();
            seriesBeanList.add(income);
            seriesBeanList.add(expend);

            billLineChartData.setCategories(categories);
            billLineChartData.setSeries(seriesBeanList);
            return billLineChartData;
        } else {
            return null;
        }
    }

    @PostMapping("deleteBill")
    public boolean deleteBill(@RequestBody Bill bill) {
        if (Util.isStringNull(bill.getUuid())) {
            QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("uuid", bill.getUuid());
            if (Util.isStringNull(bill.getImg())) {
                String path = Util.BILL_PATH + bill.getImg();
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            }
            return iBillService.remove(queryWrapper);
        }
        return false;
    }

    @PostMapping("deleteImg")
    public void deleteImg(@RequestBody Bill bill) {
        if (Util.isStringNull(bill.getAccountBookId()) && Util.isStringNull(bill.getAccountBookUser())
                && bill.getMoney() != 0 && Util.isStringNull(bill.getUuid())) {
            String temp = Util.BILL_PATH + bill.getImg();
            File file = new File(temp);
            if (file.exists()) {
                file.delete();
                bill.setImg(null);
                UpdateWrapper<Bill> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("uuid", bill.getUuid());
                iBillService.update(bill, updateWrapper);
            }
        }
    }

    @PostMapping("updateBill")
    public Map<String, Object> updateBill(@RequestBody Bill bill) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("succeed", 500);
        if (Util.isStringNull(bill.getAccountBookId()) && Util.isStringNull(bill.getAccountBookUser())
                && bill.getMoney() != 0 && Util.isStringNull(bill.getUuid())) {
            if (bill.getDate() == "" || bill.getDate() == null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                bill.setDate(df.format(new Date()));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("succeed", 200);
            map.put("message", bill.getUuid());
            errMap.put("message", "跟新失败");
            UpdateWrapper<Bill> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("uuid", bill.getUuid());
            return iBillService.update(bill, updateWrapper) ? map : errMap;
        }
        errMap.put("message", "数据为空");
        return errMap;
    }

    /**
     * 根据iD查询
     *
     * @param uuid
     * @return
     */
    @PostMapping("getById")
    public Bill getById(@RequestBody String uuid) {
        QueryWrapper<Bill> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid", uuid);
        return iBillService.getOne(queryWrapper);
    }

    /**
     * 图片上传
     *
     * @param file
     * @param uuid    账单ID
     * @param model
     * @param request
     * @return
     */
    @PostMapping(value = "/imgUpload")
    @ResponseBody
    public String imgFileUpload(@RequestParam(value = "file") MultipartFile file,
                                @RequestParam("uuid") String uuid,
                                Model model, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = Util.BILL_PATH; // 上传后的路径
        Long startTs = System.currentTimeMillis();//时间戳
        fileName = startTs + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs(); //路径没有就创建
        }
        try {
            file.transferTo(dest); //写入文件
            UpdateWrapper<Bill> billUpdateWrapper = new UpdateWrapper<>();
            billUpdateWrapper.eq("uuid", uuid);
            billUpdateWrapper.set("img", fileName);
            iBillService.update(null, billUpdateWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
        return "succeed";
    }

    /**
     * 查询账单（根据字段进行模糊查询）
     *
     * @param map
     * @return
     */
    @PostMapping("getBills")
    public List<BillModel> getBills(@RequestBody Map<String, String> map) {
        String name = map.get("keyword");
        String accountBookId = map.get("accountBookId");
        List<BillModel> billModels = new ArrayList<>();
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        billQueryWrapper.eq("account_book_id", accountBookId);
        billQueryWrapper.like(StringUtils.isNotEmpty(name), "message", name);
        List<Bill> billList = iBillService.list(billQueryWrapper);
        for (Bill b : billList) {
            BillModel billModel = new BillModel();
            BeanUtils.copyProperties(b, billModel); //拷贝字段相同
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("uuid", b.getAccountBookUser());
            User user = iUserService.getOne(userQueryWrapper);
            billModel.setAccoutBookUser(user);
            billModels.add(billModel);
        }
        //排序
        billModels.sort(Comparator.comparing(BillModel::getDate));//按时间排序
        Collections.reverse(billModels);//逆转
        return billModels;
    }


    /**
     * 添加账单
     *
     * @param bill
     * @return
     */
    @PostMapping("addBill")
    public Map<String, Object> addBill(@RequestBody Bill bill) {
        Map<String, Object> errMap = new HashMap<>();
        errMap.put("succeed", 500);
        if (Util.isStringNull(bill.getAccountBookId()) && Util.isStringNull(bill.getAccountBookUser()) && bill.getMoney() != 0) {
            bill.setUuid(Util.getUUID());
            if (bill.getDate() == "" || bill.getDate() == null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");//设置日期格式
                bill.setDate(df.format(new Date()));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("succeed", 200);
            map.put("message", bill.getUuid());
            errMap.put("message", "保存失败");
            return iBillService.save(bill) ? map : errMap;
        }
        errMap.put("message", "数据为空");
        return errMap;
    }
}
