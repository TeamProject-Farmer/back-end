package com.farmer.backend.service;

import com.farmer.backend.dto.admin.product.category.ResponseProductCategoryListDto;
import com.farmer.backend.dto.admin.settings.RequestCouponDto;
import com.farmer.backend.dto.admin.settings.ResponseCouponListDto;
import com.farmer.backend.entity.Coupon;
import com.farmer.backend.repository.admin.coupon.CouponRepository;
import com.farmer.backend.repository.admin.product.category.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private CouponRepository couponRepository;
    private ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    public List<Object> settingsList() {
        List<Object> settingsContents = new ArrayList<>();
        Map<String, List<ResponseCouponListDto>> couponList = new HashMap<>();
        Map<String, List<ResponseProductCategoryListDto>> categoryList = new HashMap<>();
        settingsContents.add(
                couponList.put("couponList", couponRepository.findAll()
                        .stream()
                        .map(ResponseCouponListDto::couponList)
                        .collect(Collectors.toList()))
        );
        settingsContents.add(
                categoryList.put("categoryList", productCategoryRepository.findAll()
                        .stream()
                        .map(ResponseProductCategoryListDto::productCategoryList)
                        .collect(Collectors.toList()))
        );
        return settingsContents;
    }

    @Transactional
    public ResponseEntity createCouponAction(RequestCouponDto requestCouponDto) {
//        couponRepository.save(requestCouponDto);
        return null;
    }
}
