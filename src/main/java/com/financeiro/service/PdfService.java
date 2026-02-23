package com.financeiro.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {
    
    public void gerarComprovante(Long transacaoId, Long contaId, BigDecimal valor){
        Document document = new Document();

        try {
            String nomeArquivo = "comprovante-" + transacaoId + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(nomeArquivo));
            document.open();

            document.add(new Paragraph("COMPROVANTE DE PAGAMENTO"));
            document.add(new Paragraph(""));
            document.add(new Paragraph("Transacao: " + transacaoId));
            document.add(new Paragraph("Conta: " + contaId));
            document.add(new Paragraph("Valor: R$ " + valor));
            document.add(new Paragraph("Data: " + LocalDateTime.now()));

            document.close();

            System.out.println("PDF gerado com sucesso: " + nomeArquivo);

        } catch (DocumentException | IOException e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
    
}
