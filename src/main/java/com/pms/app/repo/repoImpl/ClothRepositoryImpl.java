package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.pms.app.domain.*;
import com.pms.app.repo.ClothRepository;
import com.pms.app.repo.repoCustom.ClothRepositoryCustom;
import com.pms.app.schema.*;
import com.pms.app.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
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
                                        Boolean isRejected, Integer type, Long designId, Date locationDate, Double gauge, String setting, String reOrder, String week, Long colorId) {

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
                type,
                designId, gauge, setting, reOrder, week, colorId);
        if (locationDate != null) {
            Calendar gval = Calendar.getInstance();
            gval.setTime(locationDate);

            gval.set(Calendar.HOUR_OF_DAY, 0);
            gval.set(Calendar.MINUTE, 0);
            gval.set(Calendar.SECOND, 0);
            gval.set(Calendar.MILLISECOND, 0);
            Date startDate = gval.getTime();
// next day
            gval.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = gval.getTime();
            where.and(clothes.created.between(startDate, endTime));
        }
        return repository.findAll(where, pageable);
    }


    @Override
    public List<ClothOrderResource> findClothesForOrderAndCustomer(Integer orderNo, Long customerId) {
        QClothes clothes = QClothes.clothes;
        QPrints prints = QPrints.prints;
        return from(clothes)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.color)
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.customer.id.eq(customerId)).and(clothes.status.eq(Status.ACTIVE.toString())))
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.price.design.id.asc())
                .orderBy(clothes.color.id.asc())
                .list(new QClothOrderResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name_company,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name.concat("(").concat(clothes.print.size.name).concat(")"),
                        clothes.created,
                        clothes.deliver_date,
                        clothes.customer.name,
                        clothes.order_no)
                );

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
                                                 Integer type, Long designId, Double gauge, Date locationDate, String setting, String reOrder, String week, Long colorId) {

        QClothes clothes = QClothes.clothes;
        BooleanBuilder where = getBooleanBuilder(customerId,
                locationId, orderNo, barcode, deliverDateFrom,
                deliveryDateTo, orderDateFrom, orderDateTo, role, shippingNumber, boxNumber, isReject, clothes, type, designId, gauge, setting, reOrder, week, colorId);
        if (locationDate != null) {
            Calendar gval = Calendar.getInstance();
            gval.setTime(locationDate);

            gval.set(Calendar.HOUR_OF_DAY, 0);
            gval.set(Calendar.MINUTE, 0);
            gval.set(Calendar.SECOND, 0);
            gval.set(Calendar.MILLISECOND, 0);
            Date startDate = gval.getTime();
// next day
            gval.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = gval.getTime();
            where.and(clothes.created.between(startDate, endTime));
        }
        return from(clothes)
                .leftJoin(clothes.location)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.print.currency)
                .join(clothes.price)
                .join(clothes.price.size)
                .join(clothes.price.design)
                .leftJoin(clothes.price.yarn)
                .leftJoin(clothes.color)
                .where(where)
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
                        clothes.print.name.concat("(").concat(clothes.print.size.name).concat(")"),
                        clothes.shipping,
                        clothes.boxNumber,
                        clothes.weight,
                        clothes.isReturn,
                        clothes.created,
                        clothes.type,
                        clothes.color.name_company
                ));
    }

    @Override
    public List<ClothInvoiceResource> findClothesForProformaInvoice(int orderNo, Long customerId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.print.currency)
                .innerJoin(clothes.price)
                .innerJoin(clothes.price.design)
                .innerJoin(clothes.price.size)
                .leftJoin(clothes.price.yarn)
                .leftJoin(clothes.color)
                .innerJoin(clothes.customer)
                .innerJoin(clothes.customer.currency)
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.customer.id.eq(customerId))
                        .and(clothes.status.eq(Status.ACTIVE.toString())))
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.price.design.name.asc())
                .orderBy(clothes.color.code.asc())
                .orderBy(clothes.price.size.id.asc())
                .list(new QClothInvoiceResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name_company,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name.concat("(").concat(clothes.print.size.name).concat(")"),
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
                .join(clothes.price)
                .join(clothes.price.size)
                .join(clothes.price.design)
                .leftJoin(clothes.price.yarn)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.location)
                .leftJoin(clothes.color)
                .groupBy(clothes.location)
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .orderBy(clothes.price.design.name.asc())
                .orderBy(clothes.price.size.id.asc())
                .orderBy(clothes.color.code.asc())
                .orderBy(clothes.location.name.asc())
                .where(clothes.order_no.eq(orderNo)
                        .and(clothes.status.eq(Status.ACTIVE.toString()))
                        .and(clothes.customer.id.eq(customerId)).and((clothes.location.isNull().or(clothes.location.name.ne("SHIPPING")))))
                .list(new QClothOrderPendingResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name_company,
                        clothes.color.code,
                        clothes.print.name.concat("(").concat(clothes.print.size.name).concat(")"),
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
                        .and(clothes.status.eq(Status.ACTIVE.toString())))
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.color)
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
                        clothes.color.name_company,
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
        if (orderNumber != null && orderNumber != 0) {
            builder.and(clothes.order_no.eq(orderNumber.intValue()));
        }
        if (customerId != null && customerId != 0) {
            builder.and(clothes.customer.id.eq(customerId));
        }
        return from(clothes)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.print.currency)
                .leftJoin(clothes.color)
                .innerJoin(clothes.price)
                .where(builder.and(clothes.shipping.eq(shippingNumber))
                        .and(clothes.status.eq(Status.ACTIVE.toString())))
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
                        clothes.color.name_company,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name.concat("(").concat(clothes.print.size.name).concat(")"),
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
                .leftJoin(clothes.print.size)
                .leftJoin(clothes.color)
                .where(clothes.order_no.eq(id.intValue())
                        .and(clothes.status.eq(Status.ACTIVE.toString())))
                .groupBy(clothes.price)
                .groupBy(clothes.color)
                .groupBy(clothes.print)
                .list(new QClothWeavingResource(
                        clothes.price.design.name,
                        clothes.price.size.name,
                        clothes.color.name_company,
                        clothes.color.code,
                        clothes.count(),
                        clothes.print.name.concat("(").concat(clothes.print.size.name).concat(")"),
                        clothes.customer.name,
                        clothes.order_no,
                        clothes.print.amount,
                        clothes.price.amount
                ));
    }

    @Override
    public Page<Clothes> findAllForHistoryByDate(Long customerId, Long locationId, Integer orderNo, Long barcode, Date deliverDateFrom,
                                                 Date deliveryDateTo, Date orderDateFrom, Date orderDateTo, Pageable pageable,
                                                 String role, String shippingNumber, String boxNumber, Boolean isReject, Integer type,
                                                 Date locationDate, Long designId, Double gauge, String setting, String reOrder, String week, Long colorId) {
        QClothActivity activity = QClothActivity.clothActivity;
        QClothes clothes = activity.cloth;
        BooleanBuilder where = getBooleanBuilder(customerId,
                null,
                orderNo,
                barcode,
                deliverDateFrom,
                deliveryDateTo,
                orderDateFrom,
                orderDateTo,
                role,
                shippingNumber,
                boxNumber,
                isReject,
                clothes,
                type, designId, gauge, setting, reOrder, week, colorId);
        Calendar gval = Calendar.getInstance();
        gval.setTime(locationDate);

        gval.set(Calendar.HOUR_OF_DAY, 0);
        gval.set(Calendar.MINUTE, 0);
        gval.set(Calendar.SECOND, 0);
        gval.set(Calendar.MILLISECOND, 0);
        Date startDate = gval.getTime();
// next day
        gval.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = gval.getTime();

        if (locationId != null) {
            if (locationId != -1) {
                where.and(activity.location.id.eq(locationId));
                where.and(activity.created.between(startDate, endTime));
            } else {
                where.and(activity.cloth.created.between(startDate, endTime));

            }
        }

        JPQLQuery query = from(activity)
                .offset(pageable.getOffset());
        Long totalCount = query.count();
        return new PageImpl(query
                .where(where)
                .limit(pageable.getPageSize())
                .list(activity.cloth), pageable, totalCount);
    }

    private BooleanBuilder getBooleanBuilder(Long customerId, Long locationId, Integer orderNo, Long barcode,
                                             Date deliverDateFrom, Date deliveryDateTo, Date orderDateFrom,
                                             Date orderDateTo, String role, String shippingNumber,
                                             String boxNumber, Boolean isReject, QClothes clothes, Integer type, Long designId, Double gauge,
                                             String setting, String orderType, String week, Long colorId) {
        BooleanBuilder where = new BooleanBuilder();
        if (role != null) {
            where.and(clothes.location.name.eq(role));
        }

        if (barcode != null && barcode != 0L) {
            where.and(clothes.id.eq(barcode));
        } else if (isReject != null && isReject) {
            where.and(clothes.isReturn.isTrue());
            if (orderDateFrom != null && orderDateTo != null) {
                where.and(clothes.created.between(orderDateFrom, DateUtils.addDays(orderDateTo, 1)));

            }
        } else {
            if (orderNo != null) {
                where.and(clothes.order_no.eq(orderNo));
            }
            if (customerId != null) {
                where.and(clothes.customer.id.eq(customerId));
            }
            if (locationId != null) {
                if (locationId == -1) {
                    where.and(clothes.location.id.isNull());
                } else {
                    where.and(clothes.location.id.eq(locationId));
                }
            }
            if (deliverDateFrom != null) {
                where.and(clothes.deliver_date.goe(deliverDateFrom));
            }
            if (deliveryDateTo != null) {
                where.and(clothes.deliver_date.loe(DateUtils.addDays(deliveryDateTo, 1)));
            }
            if (orderDateFrom != null) {
                where.and(clothes.created.goe(orderDateFrom));
            }
            if (orderDateTo != null) {
                where.and(clothes.created.loe(DateUtils.addDays(orderDateTo, 1)));
            }

            if (shippingNumber != null) {
                where.and(clothes.shipping.eq(shippingNumber));
            }

            if (boxNumber != null) {
                where.and(clothes.boxNumber.eq(boxNumber));
            }

            if (setting != null) {
                where.and(clothes.price.design.setting.eq(setting));
            }
            if (orderType != null) {
                where.and(clothes.orderType.eq(OrderType.valueOf(orderType)));
            }
            if (isReject != null) {
                where.and(clothes.isReturn.isFalse());
            }
            if (type != null) {
                where.and(clothes.type.eq(type));
            }
            if (colorId != null) {
                where.and(clothes.color.id.eq(colorId));
            }
            if (designId != null) {
                where.and(clothes.price.design.id.eq(designId));
            }
            if (gauge != null) {
                where.and(clothes.price.design.gauge.eq(gauge));
            }
            if (week != null) {
                where.and(clothes.deliver_date.between(getStartDateOfWeek(week), DateUtils.addDays(getLastDateOfWeek(week), 1)));
            }
        }

        where.and(clothes.status.eq(Status.ACTIVE.toString()));
        return where;
    }

    private static Date getLastDateOfWeek(String week) {
        Calendar cal = Calendar.getInstance();
        cal.setWeekDate(cal.get(Calendar.YEAR), Integer.parseInt(week), Calendar.SATURDAY);
        return cal.getTime();
    }

    private static Date getStartDateOfWeek(String week) {
        Calendar cal = Calendar.getInstance();
        cal.setWeekDate(cal.get(Calendar.YEAR), Integer.parseInt(week), Calendar.SUNDAY);
        return cal.getTime();
    }

    @Override
    public List<ClothResource> findClothResourceByLocation(Long customerId,
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
                                                           Integer type,
                                                           Date locationDate, Long designId, Double gauge, String setting, String reOrder, String week, Long colorId) {

        QClothActivity activity = QClothActivity.clothActivity;
        QClothes clothes = QClothes.clothes;
        BooleanBuilder where = getBooleanBuilder(customerId,
                null, orderNo, barcode, deliverDateFrom,
                deliveryDateTo, orderDateFrom, orderDateTo, role, shippingNumber, boxNumber, isReject, clothes, type, designId, gauge, setting, reOrder, week, colorId);

        Calendar gval = Calendar.getInstance();
        gval.setTime(locationDate);

        gval.set(Calendar.HOUR_OF_DAY, 0);
        gval.set(Calendar.MINUTE, 0);
        gval.set(Calendar.SECOND, 0);
        gval.set(Calendar.MILLISECOND, 0);
        Date startDate = gval.getTime();
// next day
        gval.add(Calendar.DAY_OF_MONTH, 1);
        Date endTime = gval.getTime();
        BooleanBuilder locationWhere = new BooleanBuilder();
        if (locationId != null) {
            if (locationId != -1) {
                locationWhere.and(activity.location.id.eq(locationId));
                locationWhere.and(activity.created.between(startDate, DateUtils.addDays(endTime, 1)));
            }
        }
        return from(activity, clothes)
                .where(activity.cloth.id.eq(clothes.id).and(locationWhere))
                .join(clothes.price.design)
                .leftJoin(clothes.location)
                .leftJoin(clothes.print)
                .leftJoin(clothes.print.currency)
                .join(clothes.price)
                .join(clothes.price.size)
                .join(clothes.price.design)
                .leftJoin(clothes.price.yarn)
                .leftJoin(clothes.color)
                .where(where)
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
                        clothes.type,
                        clothes.color.name_company
                ));
    }

    @Override
    public List<Clothes> findForWeavingShipping(WeavingShippingDTO weavingShippingDTO, Long locationId) {
        QClothes clothes = QClothes.clothes;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (weavingShippingDTO.getExtraField() == null || weavingShippingDTO.getExtraField().isEmpty()) {
            booleanBuilder.and(clothes.extraField.isEmpty());
        } else {
            booleanBuilder.and(clothes.extraField.eq(weavingShippingDTO.getExtraField()));
        }
        return from(clothes).where(clothes.order_no.eq(weavingShippingDTO.getOrderNo())
                .and(clothes.customer.id.eq(weavingShippingDTO.getCustomerId())
                        .and(clothes.print.id.eq(weavingShippingDTO.getPrintId()))
                        .and(clothes.price.design.id.eq(weavingShippingDTO.getDesignId()))
                        .and(clothes.type.eq(1))
                        .and(clothes.status.eq(Status.ACTIVE.toString()))
                        .and(clothes.price.size.id.eq(weavingShippingDTO.getSizeId()))
                        .and(clothes.color.id.eq(weavingShippingDTO.getColorId()))
                        .and(booleanBuilder)
                        .and(clothes.location.id.isNull())))
                .limit(weavingShippingDTO.getQuantity())
                .list(clothes);
    }


    @Override
    public List<Clothes> findForEnteredWeavingShipping(WeavingShippingDTO weavingShippingDTO) {
        QClothes clothes = QClothes.clothes;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (weavingShippingDTO.getExtraField() == null || weavingShippingDTO.getExtraField().isEmpty()) {
            booleanBuilder.and(clothes.extraField.isEmpty());
        } else {
            booleanBuilder.and(clothes.extraField.eq(weavingShippingDTO.getExtraField()));
        }
        return from(clothes).where(clothes.order_no.eq(weavingShippingDTO.getOrderNo())
                .and(clothes.customer.id.eq(weavingShippingDTO.getCustomerId())
                        .and(clothes.print.id.eq(weavingShippingDTO.getPrintId()))
                        .and(clothes.price.design.id.eq(weavingShippingDTO.getDesignId()))
                        .and(clothes.type.eq(1))
                        .and(clothes.status.eq(Status.ACTIVE.toString()))
                        .and(clothes.price.size.id.eq(weavingShippingDTO.getSizeId()))
                        .and(clothes.color.id.eq(weavingShippingDTO.getColorId()))
                        .and(clothes.shipping.eq(weavingShippingDTO.getShipping()))
                        .and(clothes.boxNumber.eq(weavingShippingDTO.getBoxNumber()))
                        .and(booleanBuilder)
                        .and(clothes.location.id.isNotNull())))
                .limit(weavingShippingDTO.getQuantity())
                .list(clothes);
    }

    @Override
    public List<Customers> findRemaingWeavingCustomerByOrderNumber(Integer orderNumber, Long locationId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes).where(clothes.order_no.eq(orderNumber)
                .and(clothes.type.eq(1))
                .and(clothes.status.eq(Status.ACTIVE.toString()))
                .and(clothes.location.isNull()))
                .list(clothes.customer);
    }

    @Override
    public List<Designs> findRemaingWeavingDesignByOrderNumber(Integer orderNumber, Long customerId, Long locationId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes).where(clothes.order_no.eq(orderNumber)
                .and(clothes.location.isNull())
                .and(clothes.type.eq(1))
                .and(clothes.status.eq(Status.ACTIVE.toString()))
                .and(clothes.customer.id.eq(customerId)))
                .list(clothes.price.design);
    }

    @Override
    public List<Prints> findRemaingWeavingPrintByOrderNumber(Integer orderNumber, Long customerId, Long designId, Long locationId, Long sizeId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes).where(clothes.order_no.eq(orderNumber)
                .and(clothes.price.design.id.eq(designId))
                .and(clothes.customer.id.eq(customerId))
                .and(clothes.location.isNull())
                .and(clothes.type.eq(1))
                .and(clothes.status.eq(Status.ACTIVE.toString()))
                .and(clothes.price.size.id.eq(sizeId)))
                .list(clothes.print);
    }

    @Override
    public List<Sizes> findRemaingWeavingSizeByOrderNumber(Integer orderNumber, Long customerId, Long designId, Long locationId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes).where(clothes.order_no.eq(orderNumber)
                .and(clothes.location.id.isNull())
                .and(clothes.price.design.id.eq(designId))
                .and(clothes.location.isNull())
                .and(clothes.type.eq(1))
                .and(clothes.status.eq(Status.ACTIVE.toString()))
                .and(clothes.customer.id.eq(customerId)))
                .list(clothes.price.size);
    }

    @Override
    public List<String> getExtraFieldByOrderNumberAndCustomer(Integer orderNumber, Long customerId, Long designId, Long sizeId, Long printId, Long colorId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes).where(clothes.order_no.eq(orderNumber)
                .and(clothes.price.design.id.eq(designId))
                .and(clothes.customer.id.eq(customerId))
                .and(clothes.price.size.id.eq(sizeId))
                .and(clothes.location.isNull())
                .and(clothes.type.eq(1))
                .and(clothes.status.eq(Status.ACTIVE.toString()))
                .and(clothes.color.id.eq(colorId))
                .and(clothes.print.id.eq(printId)))
                .list(clothes.extraField);
    }

    @Override
    public List<Clothes> findByOrderNoAndCustomerAndPriceAndColorAndTypeAndStatusAndLocation(Integer order_no,
                                                                                             Long customerId, Long priceId, Long colorId, Integer type, String status, Long locationId, int quantity) {
        QClothes clothes = QClothes.clothes;
        return from(clothes)
                .where(clothes.order_no.eq(order_no)
                        .and(clothes.customer.id.eq(customerId))
                        .and(clothes.price.id.eq(priceId))
                        .and(clothes.color.id.eq(colorId))
                        .and(clothes.location.id.eq(locationId))
                        .and(clothes.type.eq(type))
                        .and(clothes.status.eq(status)))
                .limit(quantity)
                .list(clothes);
    }

    @Override
    public List<Colors> getColorsByOrderNumberAndCustomer(Integer orderNumber, Long customerId, Long designId, Long sizeId, Long printId) {
        QClothes clothes = QClothes.clothes;
        return from(clothes).where(clothes.order_no.eq(orderNumber)
                .and(clothes.price.design.id.eq(designId))
                .and(clothes.customer.id.eq(customerId))
                .and(clothes.price.size.id.eq(sizeId))
                .and(clothes.location.isNull())
                .and(clothes.type.eq(1))
                .and(clothes.status.eq(Status.ACTIVE.toString()))
                .and(clothes.print.id.eq(printId)))
                .list(clothes.color);
    }


}