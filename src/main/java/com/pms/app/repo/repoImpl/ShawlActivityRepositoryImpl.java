package com.pms.app.repo.repoImpl;

import com.pms.app.domain.QClothActivity;
import com.pms.app.domain.QShawlActivity;
import com.pms.app.domain.ShawlActivity;
import com.pms.app.repo.ShawlActivityRepository;
import com.pms.app.repo.repoCustom.ShawlActivityRepositoryCustom;
import com.pms.app.schema.ActivityResource;
import com.pms.app.schema.QActivityResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * Created by arjun on 6/30/2017.
 */
public class ShawlActivityRepositoryImpl extends AbstractRepositoryImpl<ShawlActivity, ShawlActivityRepository> implements ShawlActivityRepositoryCustom {
    public ShawlActivityRepositoryImpl() {
        super(ShawlActivity.class);
    }

    @Lazy
    @Autowired
    public void setRepository(ShawlActivityRepository repository) {
        this.repository = repository;

    }

    @Override
    public List<ActivityResource> findAllActivityForShawl(Long entryId) {
        QShawlActivity shawlActivity = QShawlActivity.shawlActivity;
        return from(shawlActivity)
                .innerJoin(shawlActivity.shawlEntry)
                .innerJoin(shawlActivity.location)
                .innerJoin(shawlActivity.user)
                .where(shawlActivity.shawlEntry.id.eq(entryId))
                .list(new QActivityResource(
                        shawlActivity.id,
                        shawlActivity.location.name,
                        shawlActivity.created,
                        shawlActivity.user.username
                ));
    }
}
