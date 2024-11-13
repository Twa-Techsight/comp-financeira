package com.twa.financeira.dto;
import java.util.function.DoubleUnaryOperator;

import lombok.Data;
@Data
public class ResolucaoTrapezio {
    private Double raiz;
    
    private Integer iteracoesTotal;
    
    private Double errRaiz;

    public ResolucaoTrapezio(DoubleUnaryOperator func, double x0, double x1, double err, int iteracoes) {
        achaRaiz(func, x0, x1, err, iteracoes);
    }

    public void achaRaiz(DoubleUnaryOperator func, double x0, double x1, double err, int iteracoes) {
        double y0, y1;

        y0 = func.applyAsDouble(x0);
        if (diffErrAceitavel(y0, err)) {
            iteracoesTotal = 0;
            raiz = x0;
            errRaiz = -y0;
            return;
        }

        for (int it = 0; it < iteracoes; it++) {
            y1 = func.applyAsDouble(x1);

            if (diffErrAceitavel(y1, err)) {
                iteracoesTotal = it + 1;
                raiz = x1;
                errRaiz = -y1;
                return;
            }

            // entrou em laço infinito =(
            if (y1 == y0) {
                break;
            }

            // próximo passo, achando o próximo x
            double x2 = x0 - y0*(x1 - x0)/(y1 - y0);

            // atualizando: x_{i} <-- x_{i-1}
            x0 = x1;
            x1 = x2;

            // atualizando: y0 recebe o último y encontrado
            y0 = y1;
        }
        iteracoesTotal = null;
        raiz = null;
        errRaiz = null;
        return;
    }

    private static boolean diffErrAceitavel(double y0, double err) {
        return Math.abs(y0) < err;
    }    

}
