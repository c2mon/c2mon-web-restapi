/******************************************************************************
 * Copyright (C) 2010-2016 CERN. All rights not expressly granted are reserved.
 * 
 * This file is part of the CERN Control and Monitoring Platform 'C2MON'.
 * C2MON is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the license.
 * 
 * C2MON is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for
 * more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with C2MON. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************************/
package cern.c2mon.web.restapi.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cern.c2mon.client.core.service.AlarmService;
import cern.c2mon.shared.client.alarm.AlarmValue;
import cern.c2mon.web.restapi.exception.UnknownResourceException;

import static cern.c2mon.web.restapi.version.ApiVersion.API_V1;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Controller entry point for alarm API requests.
 *
 * @author Justin Lewis Salmon
 */
@Controller
public class AlarmController {

  /**
   * The URL mapping to be used for retrieving alarms.
   */
  private static final String ALARM_VALUE_MAPPING = "/alarms/{id}";

  /**
   * Reference to the alarm service bean.
   */
  @Autowired
  private AlarmService service;

  /**
   * Spring MVC request mapping entry point for requests to the URL defined by
   * ALARM_VALUE_MAPPING.
   *
   * <p>
   * Note: only GET requests are allowed to this URL.
   * </p>
   *
   * @param id the path variable representing the ID of the alarm to be
   *          retrieved
   * @return the {@link AlarmValue} object itself, which will be automatically
   *         serialised by Spring
   *
   * @throws UnknownResourceException if no alarm was found with the given ID
   */
  @RequestMapping(value = ALARM_VALUE_MAPPING, method = GET, produces = { API_V1 })
  @ResponseBody
  public AlarmValue getAlarmValue(@PathVariable final Long id) throws UnknownResourceException {
    List<AlarmValue> list = (List<AlarmValue>) service.getAlarms(Collections.singletonList(id));

    if (list.isEmpty()) {
      throw new UnknownResourceException("No alarm with id " + id + " was found.");
    } else {
      return list.get(0);
    }
  }
}
