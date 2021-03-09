package com.atguigu.jxc.controller;

import com.atguigu.jxc.domain.PurchaseListAndGoods;
import com.atguigu.jxc.domain.ServiceVO;
import com.atguigu.jxc.domain.SuccessCode;
import com.atguigu.jxc.entity.PurchaseList;
import com.atguigu.jxc.service.PurchaseListGoodsService;
import com.atguigu.jxc.service.PurchaseListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("purchaseListGoods")
public class PurchaseListGoodsController {

    @Autowired
    private PurchaseListGoodsService purchaseListGoodsService;

    @Autowired
    private PurchaseListService purchaseListService;

    /**
     * 更新进货订单的付款状态
     * @param purchaseListId
     * @return
     */
    @PostMapping("updateState")
    public ServiceVO<String> updateState(@RequestParam("purchaseListId") Integer purchaseListId) {
        purchaseListGoodsService.updateState(purchaseListId);
        return new ServiceVO<>(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS);
    }

    /**
     * 按条件查询采购列表
     * @param sTime
     * @param eTime
     * @param goodsTypeId
     * @param codeOrName
     * @return
     */
    @PostMapping("count")
    public String count(
            @RequestParam("sTime") String sTime,
            @RequestParam("eTime") String eTime,
            @RequestParam("goodsTypeId") Integer goodsTypeId,
            @RequestParam("codeOrName") String codeOrName
    ) {
        return purchaseListGoodsService.count(sTime, eTime, goodsTypeId, codeOrName);
    }

    /**
     * 进货单保存
     * @param purchaseNumber
     * @return
     */
    @PostMapping("save")
    public ServiceVO save(
            PurchaseListAndGoods purchaseListAndGoods,
            @RequestParam("purchaseNumber") String purchaseNumber,
            HttpServletRequest request
    ){

        PurchaseList purchaseList = new PurchaseList();
        BeanUtils.copyProperties(purchaseListAndGoods, purchaseList);

        String purchaseListGoodsStr = purchaseListAndGoods.getPurchaseListGoodsStr();

        this.purchaseListService.save(purchaseList, purchaseNumber, request.getSession());

        Integer purchaseListId = purchaseList.getPurchaseListId();
        this.purchaseListGoodsService.save(purchaseListGoodsStr, purchaseListId);


        return new ServiceVO(SuccessCode.SUCCESS_CODE, SuccessCode.SUCCESS_MESS, null);
    }
}
