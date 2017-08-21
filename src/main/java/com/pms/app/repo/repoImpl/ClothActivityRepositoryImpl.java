package com.pms.app.repo.repoImpl;

import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPQLQuery;
import com.pms.app.domain.ClothActivity;
import com.pms.app.domain.QClothActivity;
import com.pms.app.repo.ClothActivityRepository;
import com.pms.app.repo.repoCustom.ClothActivityRepositoryCustom;
import com.pms.app.schema.ActivityResource;
import com.pms.app.schema.PageResult;
import com.pms.app.schema.QActivityResource;
import com.pms.app.schema.QUserHistoryResource;
import com.pms.app.schema.UserHistoryResource;
import com.pms.app.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by arrowhead on 7/16/16.
 */
public class ClothActivityRepositoryImpl extends AbstractRepositoryImpl<ClothActivity, ClothActivityRepository> implements ClothActivityRepositoryCustom {
    public ClothActivityRepositoryImpl() {
        super(ClothActivity.class);
    }

    @Autowired
    @Lazy
    public void setRepository(ClothActivityRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ActivityResource> findAllClothActivityByClothId(Long clothId) {
        QClothActivity clothActivity = QClothActivity.clothActivity;
        return from(clothActivity)
                .innerJoin(clothActivity.cloth)
                .innerJoin(clothActivity.location)
                .innerJoin(clothActivity.user)
                .where(clothActivity.cloth.id.eq(clothId))
                .list(new QActivityResource(
                        clothActivity.id,
                        clothActivity.location.name,
                        clothActivity.created,
                        clothActivity.user.username
                ));
    }

    @Override
    public Long doesActivityExist(Long locationId, Long clothesId) {
        QClothActivity clothActivity = QClothActivity.clothActivity;
        return from(clothActivity)
                .innerJoin(clothActivity.cloth)
                .innerJoin(clothActivity.user)
                .where(clothActivity.cloth.id.eq(clothesId)
                        .and(clothActivity.location.id.eq(locationId)))
                .count();
    }

    @Override
    public PageResult<UserHistoryResource> findActivities(Date completedDate, Date dateFrom, Date dateTo, String role, Integer orderNo, Pageable pageable) {
        QClothActivity clothActivity = QClothActivity.clothActivity;


        JPQLQuery query = getQuery(clothActivity, getWhereCondition(clothActivity, completedDate, dateFrom, dateTo, role,orderNo));
        Long totalCount = query.count();

        List<UserHistoryResource> batchEntries = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .list(new QUserHistoryResource(
                        clothActivity.created,
                        clothActivity.cloth.deliver_date,
                        clothActivity.cloth.order_no,
                        clothActivity.cloth.customer.name,
                        clothActivity.cloth.price.design.name,
                        clothActivity.cloth.price.yarn.name,
                        clothActivity.cloth.price.size.name,
                        clothActivity.cloth.price.design.gauge,
                        clothActivity.cloth.price.design.setting,
                        clothActivity.cloth.orderType.stringValue(),
                        clothActivity.cloth.color.code
                ));

        return new PageResult<>(totalCount, pageable.getPageSize(), pageable.getPageNumber(), batchEntries);
    }

    private JPQLQuery getQuery(QClothActivity clothActivity, BooleanBuilder whereCondition) {
        return from(clothActivity)
                .where(whereCondition);
    }

    private BooleanBuilder getWhereCondition(QClothActivity clothActivity, Date completedDate, Date dateFrom, Date dateTo, String role,Integer orderNo) {
        BooleanBuilder where = new BooleanBuilder();
        if (completedDate != null) {
            Calendar gval = Calendar.getInstance();
            gval.setTime(completedDate);

            gval.set(Calendar.HOUR_OF_DAY, 0);
            gval.set(Calendar.MINUTE, 0);
            gval.set(Calendar.SECOND, 0);
            gval.set(Calendar.MILLISECOND, 0);
            Date startDate = gval.getTime();
            gval.add(Calendar.DAY_OF_MONTH, 1);
            Date endTime = gval.getTime();
            where.and(clothActivity.created.between(startDate, endTime));
        }

        if (dateFrom != null) {
            where.and(clothActivity.created.goe(dateFrom));
        }
        if (dateTo != null) {
            where.and(clothActivity.created.loe(DateUtils.addDays(dateTo, 1)));
        }
        if(orderNo != null){
            where.and(clothActivity.cloth.order_no.eq(orderNo));
        }
        where.and(clothActivity.location.name.eq(role));
        return where;
    }

    @Override
    public List<UserHistoryResource> findActivitiesReport(Date completedDate, Date dateFrom, Date dateTo, String role, Integer orderNo) {
        QClothActivity clothActivity = QClothActivity.clothActivity;
        JPQLQuery query = getQuery(clothActivity, getWhereCondition(clothActivity, completedDate, dateFrom, dateTo, role,orderNo));

        return query.list(new QUserHistoryResource(
                clothActivity.created,
                clothActivity.cloth.deliver_date,
                clothActivity.cloth.order_no,
                clothActivity.cloth.customer.name,
                clothActivity.cloth.price.design.name,
                clothActivity.cloth.price.yarn.name,
                clothActivity.cloth.price.size.name,
                clothActivity.cloth.price.design.gauge,
                clothActivity.cloth.price.design.setting,
                clothActivity.cloth.orderType.stringValue(),
                clothActivity.cloth.color.code
        ));
    }
}

