package com.pms.app.schema;

import com.mysema.query.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Builder;

import java.util.Date;

/**
 * Created by arrowhead on 7/16/16.
 */

@Getter
@Setter
@NoArgsConstructor
public class ActivityResource {

    private Long id;
    private String location;
    private Date created;
    private String username;


    @QueryProjection
    public ActivityResource(Long id,
                            String location,
                            Date created,
                            String username) {

        this.id = id;
        this.location = location;
        this.created = created;
        this.username = username;
    }

}
