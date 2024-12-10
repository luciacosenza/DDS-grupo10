package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.service.i18n.I18nService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminViewController {

    private final Migrador migrador;
    private final AlertaService alertaService;
    private final ReporteService reporteService;

    private final I18nService i18nService;

    public AdminViewController(Migrador vMigrador, AlertaService vAlertaService, ReporteService vReporteService, I18nService vi18nService) {
        migrador = vMigrador;
        alertaService = vAlertaService;
        reporteService = vReporteService;

        i18nService = vi18nService;
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

    @PostMapping("/cargar-archivo")
    public String cargarArchivo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            migrador.migrar(file, false);

            redirectAttributes.addFlashAttribute("message", i18nService.getMessage("controller.MigradorController.cargarArchivo"));
            redirectAttributes.addFlashAttribute("message-type", "success");    // Clases Bootstrap

            return "redirect:/admin";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", i18nService.getMessage("controller.MigradorController.cargarArchivo_err"));
            redirectAttributes.addFlashAttribute("message-type", "danger"); // Clases Bootstrap

            return "redirect:/admin";
        }
    }
}
