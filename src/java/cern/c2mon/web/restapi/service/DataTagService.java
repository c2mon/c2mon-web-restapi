/*******************************************************************************
 * This file is part of the Technical Infrastructure Monitoring (TIM) project.
 * See http://ts-project-tim.web.cern.ch
 *
 * Copyright (C) 2004 - 2014 CERN. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this program; if not,
 * write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: TIM team, tim.support@cern.ch
 ******************************************************************************/
package cern.c2mon.web.restapi.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cern.c2mon.client.common.tag.ClientDataTagValue;
import cern.c2mon.web.restapi.exception.UnknownResourceException;

/**
 * @author Justin Lewis Salmon
 */
@Service
public class DataTagService {

  /**
   *
   */
  @Autowired
  private ServiceGateway gateway;

  /**
   * @param id
   * @return
   * @throws UnknownResourceException
   */
  public ClientDataTagValue getDataTagValue(Long id) throws UnknownResourceException {
    List<ClientDataTagValue> list = (List<ClientDataTagValue>) gateway.getTagManager().getDataTags(Arrays.asList(id));

    if (list.isEmpty()) {
      throw new UnknownResourceException("No datatag with id " + id + " was found.");
    } else {
      return list.get(0);
    }
  }
}
