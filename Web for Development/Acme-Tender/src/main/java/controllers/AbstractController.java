/*
 * AbstractController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import exceptions.HackingException;
import services.ConfigurationService;

@Controller
public class AbstractController {

	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private MessageSource			messageSource;


	// Panic handler ----------------------------------------------------------
	@ExceptionHandler(Throwable.class)
	public ModelAndView panic(final Throwable oops) {
		ModelAndView result;
		if (oops.getCause() instanceof HackingException)
			result = new ModelAndView("misc/hacking");
		else if (oops.getMessage().equals("user.not.logged"))
			result = new ModelAndView("redirect:/security/login.do");
		else {
			result = new ModelAndView("misc/panic");
			result.addObject("name", ClassUtils.getShortName(oops.getClass()));
			result.addObject("exception", oops.getMessage());
			result.addObject("stackTrace", ExceptionUtils.getStackTrace(oops));
		}

		return result;
	}

	public ModelAndView createMessageModelAndView(final String messageText, final String goBackUrl) {
		ModelAndView result;

		result = new ModelAndView("misc/message");

		final Locale locale = LocaleContextHolder.getLocale();

		// Buscamos el mensaje en los messages.properties...
		final String newMessage = this.messageSource.getMessage(messageText, null, messageText, locale);

		result.addObject("messageText", newMessage);
		result.addObject("goBackUrl", goBackUrl);
		result.addObject("message", null);

		return result;
	}

	/**
	 *
	 * @return banner URL of the system as a model attribute to be used in any other view
	 */
	@ModelAttribute(value = "banner")
	public String banner() {
		return this.configurationService.findBanner();
	}

	/**
	 *
	 * @return companyName of the system as a model attribute to be used in any other view
	 */
	@ModelAttribute(value = "companyName")
	public String companyName() {
		return this.configurationService.findCompanyName();
	}

	/**
	 *
	 * @return welcomeMessage of the system as a model attribute to be used in any other view
	 */
	@ModelAttribute(value = "welcomeMessage")
	public String welcomeMessage() {
		final Locale locale = LocaleContextHolder.getLocale();
		return this.configurationService.findWelcomeMessage(locale);
	}

	/**
	 *
	 * @return welcomeMessage of the system as a model attribute to be used in any other view
	 */
	@ModelAttribute(value = "benefitPercentage")
	public Double benefitPercentage() {
		return this.configurationService.findBenefitsPercentage();
	}
}
