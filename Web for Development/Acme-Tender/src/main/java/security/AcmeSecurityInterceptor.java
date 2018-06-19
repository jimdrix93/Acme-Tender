
package security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import domain.Actor;
import services.ActorService;
import services.AnswerService;
import services.CategoryService;
import services.CollaborationRequestService;
import services.CommentService;
import services.CompanyResultService;
import services.ConfigurationService;
import services.CurriculumService;
import services.EvaluationCriteriaService;
import services.EvaluationCriteriaTypeService;
import services.FileService;
import services.FolderService;
import services.MyMessageService;
import services.OfferService;
import services.SubSectionEvaluationCriteriaService;
import services.SubSectionService;
import services.TabooWordService;
import services.TenderResultService;
import services.TenderService;

public class AcmeSecurityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	ActorService						actorService;
	@Autowired
	OfferService						offerService;
	@Autowired
	TenderService						tenderService;
	@Autowired
	SubSectionService					subSectionService;
	@Autowired
	CurriculumService					curriculumService;
	@Autowired
	EvaluationCriteriaService			evaluationCriteriaService;
	@Autowired
	EvaluationCriteriaTypeService		evaluationCriteriaTypeService;
	@Autowired
	SubSectionEvaluationCriteriaService	subSectionEvaluationCriteriaService;
	@Autowired
	TabooWordService					tabooWordService;
	@Autowired
	ConfigurationService				configurationService;
	@Autowired
	TenderResultService					tenderResultService;
	@Autowired
	CompanyResultService				companyResultService;
	@Autowired
	CommentService						commentService;
	@Autowired
	AnswerService						answerService;
	@Autowired
	CategoryService						categoryService;
	@Autowired
	FileService							fileService;
	@Autowired
	CollaborationRequestService			collaborationRequestService;
	@Autowired
	FolderService						folderService;
	@Autowired
	MyMessageService					myMessageService;


//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//		//Url para mostrar "Sin permiso"
//		String accessDeniedUrl = "/info/accessDenied.do";
//
//		//Obtenemos actor logado
//		Actor actor = null;
//		try {
//			actor = this.actorService.findByPrincipal();
//		} catch (Exception e) {
//
//		}
//		//Si no está logado, dejamos pasar (meter registro, login, idiomas y welcome nada mas)
//		if (actor == null)
//			return true;
//
//		//		//Obtenemos url e id solicitadas
//		//		String url = request.getRequestURL().toString();
//		//		Integer urlId = 0;
//		//		if (request.getQueryString() != null && request.getQueryString().matches(".*\\d+.*"))
//		//			urlId = Integer.parseInt(request.getQueryString().replaceAll("[\\D]", ""));
//		//
//		//		//Comprobamos que tiene permiso de acceso... si no tiene devolvemos false
//		//		//Oferta
//		//		if (url.contains("/offer/commercial/edit.do")) {
//		//			Offer offer = this.offerService.findOne(urlId);
//		//			if (offer.getCommercial().getId() != actor.getId()) {
//		//				response.sendRedirect(request.getContextPath() + accessDeniedUrl);
//		//				return false;
//		//			}
//		//		}
//
//		//Dejamos pasar
//		return true;
//	}
}
