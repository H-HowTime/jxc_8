package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.service.SaleListGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hehao
 * @create 2021-03-08 12:46
 */
@RestController
@RequestMapping("saleListGoods")
public class SaleListGoodsController {

    @Autowired
    private SaleListGoodsService saleListGoodsService;

    /**
     * 支付结算，修改销售单付款状态
     *
     * @return
     */
    @PostMapping("/updateState")
    public ServiceVO updateState(@RequestParam("saleListId") Integer saleListId) {
        this.saleListGoodsService.updateState(saleListId);
        return new ServiceVO(100, "请求成功");
    }

    /**
     * 商品销售统计 --根据商品类别/商品编码/名称 条件查询
     *
     * @return
     */
    @PostMapping("/count")
    public String count(@RequestParam("sTime") String sTime,
                        @RequestParam("eTime") String eTime,
                        @RequestParam("goodsTypeId") Integer goodsTypeId,
                        @RequestParam("codeOrName") String codeOrName) {
        String saleListGoodsVosJson = this.saleListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
        return saleListGoodsVosJson;
    }

    /**
     * 按日统计分析
     *
     * @return
     */
    @PostMapping("/getSaleDataByDay")
    public String getSaleDataByDay(@RequestParam("sTime") String sTime,
                                   @RequestParam("eTime") String eTime) {
        String saleDataDayVosJson = this.saleListGoodsService.getSaleDataByDay(sTime, eTime);
        return saleDataDayVosJson;
    }
}
