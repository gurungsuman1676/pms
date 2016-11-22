package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.pms.app.domain.Clothes;
import com.pms.app.domain.QClothes;
import com.pms.app.domain.QPrints;
import com.pms.app.domain.Status;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.repoCustom.ClothRepositoryCustom;
import com.pms.app.schema.*;
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
                                        Boolean isRejected, Integer type) {

        QClothes clothes = QClothes.clothes;
        BooleanBuilder where = getBooleanBuilder(customerId,
                locationId,
                orderNo,
                barcode,
                deliverDateFrom,
                deliveryDateTo,
                orderDateFrom,
                orderDateTo,
                role,
                shippingNumber,
                boxNumber,
                isRejected,
                clothes,
                type);
        return repository.findAll(where, pageable);
    }


    @Override
    public List<ClothOrderResource> findClothesForOrderAndCustomer(Integer orderNo, Long customerId) {
        QClothes clothes = QClothes.clothes;
        QPrints prints = QPrints.prints;
        return from(clothes)
                .leftJoin(clothes.print)
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.type.eq(0))
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
                                                 Boolean isReject,
                                                 Integer type) {

        QClothes clothes = QClothes.clothes;
        BooleanBuilder where = getBooleanBuilder(customerId,
                locationId, orderNo, barcode, deliverDateFrom,
                deliveryDateTo, orderDateFrom, orderDateTo, role, shippingNumber, boxNumber, isReject, clothes, 0);
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
                        clothes.created,
                        clothes.type
                ));
    }

    @Override
    public List<ClothInvoiceResource> findClothesForProformaInvoice(int orderNo, Long customerId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.currency)
                .innerJoin(clothes.price)
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.type.eq(0))
                        .and(clothes.customer.id.eq(customerId)))
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.price.design.name.asc())
                .orderBy(clothes.color.code.asc())
                .orderBy(clothes.price.size.id.asc())
                .list(new QClothInvoiceResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name,
                        clothes.created,
                        clothes.deliver_date,
                        clothes.customer.name,
                        clothes.order_no,
                        clothes.boxNumber,
                        clothes.print.amount,
                        clothes.print.currency.name,
                        clothes.shipping,
                        clothes.price.amount,
                        clothes.customer.currency.name
                ));
    }

    @Override
    public List<ClothOrderPendingResource> findClothesPendingForOrderAndCustomer(int orderNo, Long customerId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes)
                .leftJoin(clothes.print)
                .leftJoin(clothes.location)
                .groupBy(clothes.location)
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.price.design.name.asc())
                .orderBy(clothes.price.size.id.asc())
                .orderBy(clothes.color.code.asc())
                .orderBy(clothes.location.name.asc())
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.type.eq(0))
                        .and(clothes.customer.id.eq(customerId)).and((clothes.location.isNull().or(clothes.location.name.ne("SHIPPING")))))
                .list(new QClothOrderPendingResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name,
                        clothes.color.code,
                        clothes.print.name,
                        clothes.created,
                        clothes.deliver_date,
                        clothes.customer.name,
                        clothes.order_no,
                        clothes.location.name,
                        clothes.count()
                ));
    }

    @Override
    public List<ClothShippingResource> findShippedCloth(String shippingNumber) {
        QClothes clothes = QClothes.clothes;
        return from(clothes)
                .where(clothes.shipping.eq(shippingNumber)
                        .and(clothes.type.eq(0)))
                .leftJoin(clothes.print)
                .groupBy(clothes.boxNumber)
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.boxNumber.asc())
                .orderBy(clothes.order_no.asc())
                .orderBy(clothes.price.design.name.asc())
                .orderBy(clothes.color.code.asc())
                .orderBy(clothes.price.size.id.asc())
                .list(new QClothShippingResource(
                        clothes.id,
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name,
                        clothes.order_no,
                        clothes.count(),
                        clothes.boxNumber,
                        clothes.color.code

                ));
    }

    @Override
    public List<ClothInvoiceResource> findInvoice(Long orderNumber, Long customerId, String shippingNumber) {
        QClothes clothes = QClothes.clothes;


        BooleanBuilder builder = new BooleanBuilder();
        if(orderNumber != null && orderNumber != 0){
            builder.and(clothes.order_no.eq(orderNumber.intValue()));
        }
        if(customerId != null && customerId != 0){
            builder.and(clothes.customer.id.eq(customerId));
        }
        return from(clothes)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.currency)
                .innerJoin(clothes.price)
                .where(builder.and(clothes.shipping.eq(shippingNumber))
                        .and(clothes.type.eq(1)))
                .groupBy(clothes.boxNumber)
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.boxNumber.asc())
                .orderBy(clothes.order_no.asc())
                .orderBy(clothes.price.design.name.asc())
                .orderBy(clothes.color.code.asc())
                .orderBy(clothes.price.size.id.asc())
                .list(new QClothInvoiceResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name,
                        clothes.created,
                        clothes.deliver_date,
                        clothes.customer.name,
                        clothes.order_no,
                        clothes.boxNumber,
                        clothes.print.amount,
                        clothes.print.currency.name,
                        clothes.shipping,
                        clothes.price.amount,
                        clothes.customer.currency.name
                ));
    }

    @Override
    public List<ClothWeavingResource> findClothesForOrder(Long id) {
        QClothes clothes = QClothes.clothes;
        QPrints prints = QPrints.prints;
        return from(clothes)
                .leftJoin(clothes.print)
                .where(clothes.order_no.eq(id.intValue())
                .and(clothes.type.eq(1)))
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .list(new QClothWeavingResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name,
                        clothes.customer.name,
                        clothes.order_no,
                        clothes.print.amount,
                        clothes.price.amount
                ));
    }

    private BooleanBuilder getBooleanBuilder(Long customerId, Long locationId, Integer orderNo, Long barcode,
                                             Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom,
                                             Date orderDateTo, String role, String shippingNumber,
                                             String boxNumber, Boolean isReject, QClothes clothes, Integer type) {
        BooleanBuilder where = new BooleanBuilder();
        if (role != null) {
            where.and(clothes.location.name.eq(role));
        }

        if (barcode != null && barcode != 0L) {
            where.and(clothes.id.eq(barcode));
        } else if (isReject != null && isReject) {
            where.and(clothes.isReturn.isTrue());
            if (orderDateFrom != null && orderDateTo != null) {
                where.and(clothes.created.between(orderDateFrom, orderDateTo));

            }
        } else {
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
            if (orderDateFrom != null) {
                where.and(clothes.created.after(orderDateFrom));
            }
            if (orderDateTo != null) {
                where.and(clothes.created.before(orderDateTo));
            }

            if (shippingNumber != null) {
                where.and(clothes.shipping.eq(shippingNumber));
            }

            if (boxNumber != null) {
                where.and(clothes.boxNumber.eq(boxNumber));
            }

            if (isReject != null) {
                where.and(clothes.isReturn.isFalse());
            }
            if(type != null){
                where.and(clothes.type.eq(type));
            }
        }

        where.and(clothes.status.eq(Status.ACTIVE.toString()));
        return where;
    }

}