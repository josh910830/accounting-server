package com.github.suloginscene.accountant.root.api;

import com.github.suloginscene.accountant.common.Holder;
import com.github.suloginscene.accountant.root.api.representation.IndexRepresentation;
import com.github.suloginscene.accountant.root.application.RootClearingService;
import com.github.suloginscene.security.Authenticated;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RootRestController {

    private final RootClearingService rootClearingService;


    @GetMapping
    ResponseEntity<IndexRepresentation> getIndex() {
        return ResponseEntity.ok(IndexRepresentation.CONSTANT);
    }

    @DeleteMapping
    ResponseEntity<Void> clear(@Authenticated Long memberId) {
        Holder holder = new Holder(memberId);

        rootClearingService.clearAll(holder);

        return ResponseEntity.noContent().build();
    }

}
