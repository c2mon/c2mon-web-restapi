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
package cern.c2mon.web.restapi.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

import cern.c2mon.client.common.listener.BaseTagListener;
import cern.c2mon.client.common.tag.Tag;
import cern.c2mon.client.core.service.ConfigurationService;
import cern.c2mon.client.core.service.TagService;
import cern.c2mon.shared.client.tag.TagConfig;
import cern.c2mon.web.restapi.exception.UnknownResourceException;

/**
 * Service bean for accessing {@link Tag} objects from the C2MON server.
 *
 * @author Justin Lewis Salmon
 */
@Service
@Slf4j
public class TagServiceProxy implements BaseTagListener {

  @Autowired
  private TagService tagService;

  @Autowired
  private ConfigurationService configurationService;

  /**
   * Retrieve a {@link Tag} object.
   *
   * @param id the ID of the {@link Tag} to retrieve
   * @return the {@link Tag} object
   *
   * @throws UnknownResourceException if no tag could be found with the given ID
   */
  public Tag getTag(Long id) throws UnknownResourceException {
    // Try to get the tag from the cache
    if (!tagService.getSubscriptionIds(this).contains(id)) {
      tagService.subscribe(id, this);
    }

    return tagService.get(id);
  }

  /**
   * Retrieve a {@link TagConfig} object.
   *
   * @param id the ID of the {@link TagConfig} to retrieve
   * @return the {@link TagConfig} object
   *
   * @throws UnknownResourceException if no tag could be found with the given ID
   */
  public TagConfig getTagConfig(Long id) throws UnknownResourceException {
    TagConfig tagConfig;

    List<TagConfig> list = (List<TagConfig>) configurationService.getTagConfigurations(Collections.singletonList(id));
    if (list.isEmpty()) {
      throw new UnknownResourceException("No tag with id " + id + " was found.");
    } else {
      tagConfig = list.get(0);
    }

    return tagConfig;
  }

  @Override
  public void onUpdate(Tag tag) {
    log.trace("Got update for tag #{}", tag.getId());
  }
}
