/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.executive;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.AdministrativeRequest;
import domain.CollaborationRequest;
import domain.Executive;
import domain.Offer;
import domain.Tender;
import exceptions.HackingException;
import services.ActorService;
import services.DashboardService;
import services.ExecutiveService;

@Controller
@RequestMapping("/dashboard/executive")
public class DashboardExecutiveController extends AbstractController {

	@Autowired
	DashboardService dashboardService;

	// Services ---------------------------------------------------------------
	@Autowired
	ActorService actorService;
	@Autowired
	ExecutiveService executiveService;

	// Constructors -----------------------------------------------------------

	public DashboardExecutiveController() {
		super();
	}

	@RequestMapping(value = "/display")
	public ModelAndView create() throws HackingException {
		ModelAndView result;
		result = new ModelAndView("dashboard/executive/display");

		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Executive);

		final Collection<Object> consultaC1 = this.executiveService.numberTenderByAdministrative();
		result.addObject("c1Datos", consultaC1);

		final Collection<Object> consultaC2 = this.executiveService.tendersByInterestLevel();
		result.addObject("c2Datos", consultaC2);

		final Collection<Object> consultaC3 = this.executiveService.offersByState();
		result.addObject("c3Datos", consultaC3);

		final Collection<Object> consultaC4 = this.executiveService.offersByStateAndCommercial();
		result.addObject("c4Datos", consultaC4);

		final Collection<Object> consultaC5 = this.executiveService.offersByStateRatio();
		result.addObject("c5Datos", consultaC5);

		final Collection<Object> consultaC6 = this.executiveService.offersByStateAndCommercialRatio();
		result.addObject("c6Datos", consultaC6);
		/*
		 * ==================================== B ====================================
		 * 
		 * i. Top 10 por importe de oferta económica de ofertas presentadas el último mes.
		 */
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now); // Configuramos la fecha actual
		calendar.add(Calendar.MONTH, -1); // Lo ponemos al mes anterior
		final Collection<Offer> consultaB1 = this.executiveService.findTopOffersOnMonth(calendar.getTime(), 10);

		result.addObject("consultaB1", consultaB1);

		/*
		 * ii. Top 10 por importe de oferta económica de ofertas ganadas los últimos tres meses. 
		 */
		calendar.setTime(now); // Configuramos la fecha actual
		calendar.add(Calendar.MONTH, -3); // Lo ponemos al mes anterior
		final List<Offer> consultaB2 = this.executiveService.findTopWinedOffersOnThreeMonthsFrom(calendar.getTime(), now, 10);

		result.addObject("consultaB2", consultaB2);

		/*
		 * iii. Ratio medio de la oferta económica sobre el importe estimado de
		 * licitación, agrupando por empresa en resultado de concurso.
		 * 
		 * select cr.name, avg(cr.economicalOffer / t.estimatedAmount) from
		 * CompanyResult cr join cr.tenderResult tr join tr.tender t where
		 * t.estimatedAmount != 0 group by cr.name
		 * 
		 */
		final Collection<Object> consultaB3 = this.executiveService.findAvgRatioAmounOfferOverTender();

		result.addObject("consultaB3", consultaB3);

		/*
		 * iv. Top 10 de empresas existentes en resultados de concurso.
		 * 
		 * select cr.name, count(cr.name) from CompanyResult cr group by cr.name order
		 * by count(cr.name) desc
		 * 
		 */
		final Collection<Object> consultaB4 = this.executiveService.findTopFrecuentsCompanies(10);

		result.addObject("consultaB4", consultaB4);

		/*
		 * v. Top 10 de empresas ganadoras en resultados de concurso.
		 * 
		 * select cr.name, count(cr.name) from CompanyResult cr where cr.state =
		 * 'WINNER' group by cr.name order by count(cr.name) desc
		 */
		final Collection<Object> consultaB5 = this.executiveService.findTopFrecuentsWinnersCompanies(10);

		result.addObject("consultaB5", consultaB5);

		/*
		 * 
		 * ==================================== A ====================================
		 * 
		 * i. Listado de concursos con interés alto sin oferta asociada en los que
		 * queden menos de un mes para llegar a la fecha máxima de presentación.
		 * 
		 */
		calendar.setTime(now); // Configuramos la fecha actual
		Date from = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 30); // numero de días a añadir, o restar en caso de días<0
		Date to = calendar.getTime();

		final List<Tender> consultaA1 = this.executiveService.findHighInterestNoOferTendersCloseToExpire(from, to);

		result.addObject("consultaA1", consultaA1);

		/* Listado de concursos con interÃ©s alto con oferta asociada en estado
		 * abandonada en los que queden entre 10 dÃ­as y un mes para llegar a la fecha
		 * mÃ¡xima de presentaciÃ³n. ---> ESTA QUITARLA DE REQUISITOS!
		 */
		calendar.setTime(now); // Configuramos la fecha actual
		calendar.add(Calendar.DAY_OF_YEAR, 10); // numero de días a añadir, o restar en caso de días<0
		from = calendar.getTime();
		
		final List<Tender> consultaA2 = this.executiveService
				.findHighInterestTendersWithAbandonedOfferCloseToExpire(from, to);
		result.addObject("consultaA2", consultaA2);

		/*
		 * iii. Listado de solicitudes administrativas rechazadas por el usuario
		 * administrativo, mostrando el motivo.
		 * 
		 * select ar.rejectedReason from AdministrativeRequest ar where ar.accepted =
		 * false order by ar.maxAcceptanceDate desc
		 */
		final Collection<AdministrativeRequest> consultaA3 = this.executiveService.findRejectedAdministrativeRequest();
		result.addObject("consultaA3", consultaA3);
		/*
		 * iv. Listado de solicitudes de colaboraciÃ³n rechazadas por comerciales,
		 * mostrando el motivo.
		 */
		final Collection<CollaborationRequest> consultaA4 = this.executiveService.findRejectedComercialRequest();
		result.addObject("consultaA4", consultaA4);
		/*
		 * v. Media y desviaciÃ³n tÃ­pica del porcentaje de beneficios ofertado en
		 * â€œSolicitudes de colaboraciÃ³nâ€� aceptadas.
		 */
		final Collection<Object> consultaA5 = this.executiveService
				.findAvgAndDevPerncentOfferProffitOnAceptedColaborationRequests();
		result.addObject("consultaA5", consultaA5);
		/*
		 * vi. Media y desviaciÃ³n tÃ­pica del porcentaje de beneficios ofertado en
		 * â€œSolicitudes de colaboraciÃ³nâ€� rechazadas.
		 */
		final Collection<Object> consultaA6 = this.executiveService
				.findAvgAndDevPerncentOfferProffitOnRejectedColaborationRequests();
		result.addObject("consultaA6", consultaA6);

		return result;
	}
}
