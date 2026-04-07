package br.edu.fatecpg.deolhonolixo.core.usecase.user;

import br.edu.fatecpg.deolhonolixo.core.domain.exception.ConfirmPasswordMismatchExeption;
import br.edu.fatecpg.deolhonolixo.infrastructure.config.annotations.UseCase;

@UseCase
public class ConfirmPasswordMatchCase {
    public void execute(String password, String confirmPassword){
        if (!password.equals(confirmPassword)){
            throw new ConfirmPasswordMismatchExeption();
        }
    }
}
