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
package cern.c2mon.web.restapi.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import cern.c2mon.client.ext.history.common.HistoryProvider;
import cern.c2mon.client.ext.history.common.exception.HistoryProviderException;
import cern.c2mon.client.ext.history.common.exception.LoadingParameterException;

/**
 * This class handles all exceptions thrown by request handler methods and
 * converts them to proper JSON error responses.
 *
 * @author Justin Lewis Salmon
 */
@ControllerAdvice
public class RestExceptionHandler {

  /**
   * Default exception handler that will be called if no more specific exception
   * matches.
   *
   * @param e the {@link Exception} that was thrown
   * @return an {@link ErrorResponse} object that will be automatically
   *         serialised by Spring
   */
  @ExceptionHandler(Exception.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Object handleException(Exception e) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST, e);
  }

  /**
   * Exception handler for {@link TypeMismatchException}, which occurs for
   * example when a client passes a string instead of an integer as a URL
   * parameter.
   *
   * @param e the {@link TypeMismatchException} that was thrown
   * @return an {@link ErrorResponse} object that will be automatically
   *         serialised by Spring
   */
  @ExceptionHandler(TypeMismatchException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Object handleTypeMismatchException(Exception e) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST, "Bad request parameter format");
  }

  /**
   * Exception handler for {@link UnknownResourceException} which occurs when a
   * client requests a resource that does not exist.
   *
   * @param e the {@link UnknownResourceException} that was thrown
   * @return an {@link ErrorResponse} object that will be automatically
   *         serialised by Spring
   */
  @ExceptionHandler(UnknownResourceException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public Object handleUnknownResourceException(UnknownResourceException e) {
    return new ErrorResponse(HttpStatus.NOT_FOUND, e);
  }

  /**
   * Exception handler for {@link HistoryProviderException} which occurs when
   * there is a problem accessing the {@link HistoryProvider}.
   *
   * @param e the {@link HistoryProviderException} that was thrown
   * @return an {@link ErrorResponse} object that will be automatically
   *         serialised by Spring
   */
  @ExceptionHandler(HistoryProviderException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
  public Object handleHistoryProviderException(HistoryProviderException e) {
    return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  /**
   * Exception handler for {@link LoadingParameterException} which occurs when
   * there is a problem retrieving history for a resource.
   *
   * @param e the {@link LoadingParameterException} that was thrown
   * @return an {@link ErrorResponse} object that will be automatically
   *         serialised by Spring
   */
  @ExceptionHandler(LoadingParameterException.class)
  @ResponseBody
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public Object handleLoadingParameterException(LoadingParameterException e) {
    return new ErrorResponse(HttpStatus.BAD_REQUEST, e);
  }
}
