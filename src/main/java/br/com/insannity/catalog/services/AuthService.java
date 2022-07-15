package br.com.insannity.catalog.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {


    public boolean isAdmin(String codigo) {
        log.warn("isAdmin");
        log.warn(codigo);
        return true;
    }


}
