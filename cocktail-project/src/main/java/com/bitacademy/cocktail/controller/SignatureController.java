package com.bitacademy.cocktail.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitacademy.cocktail.domain.ReviewSignature;
import com.bitacademy.cocktail.domain.Signature;
import com.bitacademy.cocktail.repository.ReviewSignatureRepository;
import com.bitacademy.cocktail.service.ReviewSignatureService;
import com.bitacademy.cocktail.service.SignatureService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signature")
public class SignatureController {

	/* SignatureService, ReviewSignatureService 생성자 주입 */
	private final SignatureService signatureService;
	private final ReviewSignatureService reviewSignatureService;
	private final ReviewSignatureRepository reviewSignatureRepository;
	
	/* 시그니처 리스트 */
	@GetMapping({"", "/list"})
	public String list(Model model) {
		List<Signature> signature = signatureService.listSignature();
		model.addAttribute("signature", signature);
		return "signature/signatureList";
	}

	/* 시그니처 글 작성폼 */
	@GetMapping("/form")
	public String writeSignature() {
		return "signature/signatureForm";
	}

	/* 시그니처 글 작성 */
	@PostMapping("/form")
	public String writeSignature(@ModelAttribute Signature signature) {
		signatureService.add(signature);
		return "redirect:/signature";
	}

	/* 시그니처 게시글 보기 + 해당 게시글 댓글 리스트 */
	@GetMapping("/view")
	public String view(Long no, Model model) {
		model.addAttribute("signature", signatureService.findSigView(no));
		
		List<ReviewSignature> reviewSignature = reviewSignatureService.listReviewSignature(no);
		model.addAttribute("reviewSignature", reviewSignature);
		
		return "signature/signatureView";
	}

	/* 시그니처 게시글 삭제 */
	@GetMapping("/delete/{no}")
	public String delete(@PathVariable("no") Long no) {
		signatureService.delete(no);
		return "redirect:/signature/list";
	}

	/* 시그니처 게시글 수정폼 */
	@GetMapping("/modify/{no}")
	public String modify(@PathVariable("no") Long no, Model model) {
		// 기존 글 담아오기
		model.addAttribute("signature", signatureService.findSigView(no));
		return "signature/signatureModify";
	}

	/* 시그니처 게시글 수정 */
	@PostMapping("/modify/{no}")
	public String modify(@PathVariable("no") Long no, @ModelAttribute Signature signature) {
		signatureService.modify(signature);
		return "redirect:/signature";
	}
	
	/* 시그니처 게시글 댓글 작성 */
	@PostMapping("/review/{no}")
	public String writeReviewSig(
			@PathVariable("no") Signature signature_no,
			@ModelAttribute ReviewSignature reviewSignature) {
		reviewSignature.setSignature(signature_no);
		reviewSignatureService.add(reviewSignature);
		
		System.out.println("33333333333333333333333333333"+ signature_no);
		System.out.println("33333333333333333333333333333"+ reviewSignature);
		
		return "redirect:/signature";
	}
	
	/* 시그니처 게시글 댓글 삭제 */
	@GetMapping("/review/delete/{reviewNo}")
	public String deleteReviewSig(
			@PathVariable("reviewNo") Long reviewNo,
			@ModelAttribute ReviewSignature reviewSignature,
			@RequestParam("no") Signature signature_no) {
		reviewSignatureService.delete(reviewNo);
		return "redirect:/signature/view?no=" + signature_no;

	}

}
