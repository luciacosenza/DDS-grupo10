package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tp_anual.proyecto_heladeras_solidarias.service.incidente.AlertaService;
import com.tp_anual.proyecto_heladeras_solidarias.service.migrador.Migrador;
import com.tp_anual.proyecto_heladeras_solidarias.service.reporte.ReporteService;
import com.tp_anual.proyecto_heladeras_solidarias.model.incidente.Alerta;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.FallasPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.MovimientosViandaPorHeladera;
import com.tp_anual.proyecto_heladeras_solidarias.model.reporte.ViandasPorColaborador;

import org.springframework.ui.Model;

@Controller
public class AdminViewController {

    private final Migrador migrador;
    private final AlertaService alertaService;
    private final ReporteService reporteService;

    public AdminViewController(Migrador vMigrador, AlertaService vAlertaService, ReporteService vReporteService){
        migrador = vMigrador;
        alertaService = vAlertaService;
        reporteService = vReporteService;
    }

    @GetMapping("/admin")
    public String mostrarAdmin(Model model) {
        List<Alerta> alertas = alertaService.obtenerAlertas();
        model.addAttribute("alertas", alertas);

        List<FallasPorHeladera> fallasPorHeladera = reporteService.obtenerReporteFallasPorHeladera();
        List<MovimientosViandaPorHeladera> movimientosViandaPorHeladera = reporteService.obtenerReporteMovimientosViandaPorHeladera();
        List<ViandasPorColaborador> viandasPorColaborador = reporteService.obtenerReporteViandasPorColaborador();

        model.addAttribute("reporteFallasPorHeladera", fallasPorHeladera);
        model.addAttribute("reporteMovimientosViandaPorHeladera", movimientosViandaPorHeladera);
        model.addAttribute("reporteViandasPorColaborador", viandasPorColaborador);

        return "admin";
    }

    @PostMapping("/migrar-archivo")
    public String migrarArchivo(@RequestParam("file") MultipartFile file, Model model) {
        if (!file.isEmpty() && file.getOriginalFilename().endsWith(".csv")) {
            try {
                String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
                migrador.migrar(fileContent, true);
                model.addAttribute("message", "Archivo migrado exitosamente.");
            } catch (Exception e) {
                model.addAttribute("message", "Error al migrar archivo: " + e.getMessage());
            }
        } else {
            model.addAttribute("message", "Por favor seleccione un archivo CSV.");
        }
        return "redirect: admin";
    }
}
