package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.pms.app.domain.Clothes;
import com.pms.app.domain.QClothes;
import com.pms.app.domain.QPrints;
import com.pms.app.domain.Status;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.repoCustom.ClothRepositoryCustom;
import com.pms.app.schema.ClothOrderResource;
import com.pms.app.schema.ClothResource;
import com.pms.app.schema.QClothOrderResource;
import com.pms.app.schema.QClothResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public class ClothRepositoryImpl extends AbstractRepositoryImpl<Clothes, ClothRepository> implements ClothRepositoryCustom {
    public ClothRepositoryImpl() {
        super(Clothes.class);
    }


    @Autowired
    @Lazy
    public void setRepository(ClothRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Clothes> findAllClothes(Long customerId,
                                        Long locationId,
                                        Integer orderNo,
                                        Long barcode,
                                        Date deliverDateFrom,
                                        Date deliveryDateTo,
                                        Date orderDateFrom,
                                        Date orderDateTo,
                                        Pageable pageable,
                                        String role,
                                        String shippingNumber,
                                        String boxNumber,
                                        Boolean isRejected) {

        QClothes clothes = QClothes.clothes;
        BooleanBuilder where = getBooleanBuilder(customerId, locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom, orderDateTo, role, shippingNumber, boxNumber, isRejected, clothes);
        return repository.findAll(where, pageable);
    }



    @Override
    public List<ClothOrderResource> findClothesForOrderAndCustomer(Integer orderNo, Long customerId) {
        QClothes clothes = QClothes.clothes;
        QPrints prints = QPrints.prints;
        return from(clothes)
                .leftJoin(clothes.print)
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.customer.id.eq(customerId)))
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .list(new QClothOrderResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name,
                        clothes.created,
                        clothes.deliver_date,
                        clothes.customer.name,
                        clothes.order_no
                ));
    }

    @Override
    public List<ClothResource> findClothResource(Long customerId,
                                                 Long locationId,
                                                 Integer orderNo,
                                                 Long barcode,
                                                 Date deliverDateFrom,
                                                 Date deliveryDateTo,
                                                 Date orderDateFrom,
                                                 Date orderDateTo,
                                                 String role,
                                                 String shippingNumber,
                                                 String boxNumber,
                                                 Boolean isReject) {

        QClothes clothes = QClothes.clothes;
        BooleanBuilder where = getBooleanBuilder(customerId, locationId, orderNo, barcode, deliverDateFrom, deliveryDateTo, orderDateFrom, orderDateTo, role, shippingNumber, boxNumber, isReject, clothes);
        return from(clothes)
                .where(where)
                .leftJoin(clothes.location)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.currency)
                .list(new QClothResource(
                        clothes.id,
                        clothes.location.name,
                        clothes.price.amount,
                        clothes.customer.currency.name,
                        clothes.order_no,
                        clothes.price.design.name,
                        clothes.price.yarn.name,
                        clothes.color.code,
                        clothes.price.size.name,
                        clothes.customer.name,
                        clothes.deliver_date,
                        clothes.print.currency.name,
                        clothes.print.amount,
                        clothes.print.name,
                        clothes.shipping,
                        clothes.boxNumber,
                        clothes.weight,
                        clothes.isReturn,
                        clothes.created
                ));
    }

    private BooleanBuilder getBooleanBuilder(Long customerId, Long locationId, Integer orderNo, Long barcode,
                                             Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom,
                                             Date orderDateTo, String role, String shippingNumber,
                                             String boxNumber, Boolean isReject, QClothes clothes) {
        BooleanBuilder where = new BooleanBuilder();
        if(role != null){
            where.and(clothes.location.name.eq(role));
        }

        if (barcode != null && barcode != 0L) {
            where.and(clothes.id.eq(barcode));
        } else if(isReject != null  && isReject){
            where.and(clothes.isReturn.isTrue());
            if(orderDateFrom != null && orderDateTo != null){
                where.and(clothes.created.between(orderDateFrom,orderDateTo));

            }
        }else  {
            if (orderNo != null) {
                where.and(clothes.order_no.eq(orderNo));
            }
            if (customerId != null) {
                where.and(clothes.customer.id.eq(customerId));
            }
            if (locationId != null) {
                where.and(clothes.location.id.eq(locationId));
            }
            if (deliverDateFrom != null) {
                where.and(clothes.deliver_date.after(deliverDateFrom));
            }
            if (deliveryDateTo != null) {
                where.and(clothes.deliver_date.before(deliveryDateTo));
            }
            if (    orderDateFrom != null) {
                where.and(clothes.created.after(orderDateFrom));
            }
            if (orderDateTo != null) {
                where.and(clothes.created.before(orderDateTo));
            }

            if(shippingNumber != null){
                where.and(clothes.shipping.eq(shippingNumber));
            }

            if(boxNumber != null){
                where.and(clothes.boxNumber.eq(boxNumber));
            }

            if(isReject != null) {
                where.and(clothes.isReturn.isFalse());
            }
        }

        where.and(clothes.status.eq(Status.ACTIVE.toString()));
        return where;
    }

}