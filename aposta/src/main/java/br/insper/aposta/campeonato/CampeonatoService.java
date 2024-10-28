package br.insper.aposta.campeonato;

import br.insper.aposta.aposta.ApostaRepository;
import br.insper.aposta.partida.RetornarPartidaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CampeonatoService {

    @Autowired
    private ApostaRepository apostaRepository;

    @KafkaListener(topics = "partidas")
    public void getPartidas(RetornarPartidaDTO dto) {
        apostaRepository.findAll().forEach(aposta -> {
            if (aposta.getIdPartida().equals(dto.getId())) {
                if (dto.getStatus().equals("REALIZADA")) {
                    if (aposta.getResultado().equals("EMPATE") && dto.isEmpate()) {
                        aposta.setStatus("GANHOU");
                    }

                    if (aposta.getResultado().equals("VITORIA_MANDANTE") && dto.isVitoriaMandante()) {
                        aposta.setStatus("GANHOU");
                    }

                    if (aposta.getResultado().equals("EMPATE") && dto.isVitoriaVisitante()) {
                        aposta.setStatus("GANHOU");
                    }

                    if (aposta.getStatus().equals("REALIZADA")) {
                        aposta.setStatus("PERDEU");
                    }
                }
                apostaRepository.save(aposta);
            }
        });
    }
}
