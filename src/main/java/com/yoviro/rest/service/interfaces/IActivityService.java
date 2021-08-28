package com.yoviro.rest.service.interfaces;

import com.yoviro.rest.models.entity.Activity;
import java.util.List;

public interface IActivityService {
    Iterable<Activity> saveAll(List<Activity> activities);
}
