package com.farmer.backend.service;

import com.farmer.backend.dto.admin.product.category.ResponseProductCategoryListDto;
import com.farmer.backend.dto.admin.settings.RequestCouponDto;
import com.farmer.backend.dto.admin.settings.ResponseCouponDetailDto;
import com.farmer.backend.dto.admin.settings.ResponseCouponListDto;
import com.farmer.backend.entity.Coupon;
import com.farmer.backend.exception.CustomException;
import com.farmer.backend.exception.ErrorCode;
import com.farmer.backend.repository.admin.coupon.CouponRepository;
import com.farmer.backend.repository.admin.product.category.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.farmer.backend.exception.ErrorCode.COUPON_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettingsService {

    private final CouponRepository couponRepository;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional(readOnly = true)
    public List<Object> settingsList() {
        List<Object> settingsContents = new ArrayList<>();
        List<ResponseCouponListDto> couponList = couponRepository.findAll().stream().map(c -> ResponseCouponListDto.couponList(c)).collect(Collectors.toList());
        List<ResponseProductCategoryListDto> productCategoryList = productCategoryRepository.findAll().stream().map(pc -> ResponseProductCategoryListDto.productCategoryList(pc)).collect(Collectors.toList());
        settingsContents.add(couponList);
        settingsContents.add(productCategoryList);
        return settingsContents;
    }

    @Transactional
    public Long createCouponAction(RequestCouponDto requestCouponDto) {
        return couponRepository.save(requestCouponDto.toEntity()).getId();
    }

    @Transactional
    public String createSerialNumber() {
        int serialLengthSize = 20;
        String serialNum = "";
        char[] charNum = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

        int count = charNum.length;
        String[] arr = new String[serialLengthSize];
        Random rand = new Random();
        int curIndex = 0;
        int i = 0;

        while (curIndex < serialLengthSize) {
            StringBuffer buffer = new StringBuffer(16);
            for (i = 15; i >= 0; i--) {
                buffer.append(charNum[rand.nextInt(count)]);
                if (i % 4 == 0 && i > 0) {
                    buffer.append("-");
                }
            }
            serialNum = buffer.toString();
            arr[curIndex] = serialNum;
            curIndex++;
        }
        log.info("serialNum={}", serialNum);

        return serialNum;
    }

    @Transactional(readOnly = true)
    public ResponseCouponDetailDto findByCoupon(Long couponId) {
        return couponRepository.findById(couponId).map(coupon -> ResponseCouponDetailDto.getCouponDetail(coupon)).orElseThrow(() -> new CustomException(COUPON_NOT_FOUND));
    }

    @Transactional
    public ResponseEntity removeCouponAction(Long[] couponIds) {
        for (Long couponId : couponIds) {
            Coupon findCoupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(COUPON_NOT_FOUND));
            couponRepository.delete(findCoupon);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
