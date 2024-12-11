package com.tp_anual.proyecto_heladeras_solidarias.controller.view;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.tp_anual.proyecto_heladeras_solidarias.exception.contribucion.*;
import com.tp_anual.proyecto_heladeras_solidarias.exception.migrador.FilaDeDatosIncompletaException;
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
        model.addAttribute("messageNoAlerts", i18nService.getMessage("controller.AdminController.mostrarAdmin_no_alertas"));

        return "admin";
    }

    @PostMapping("/cargar-archivo")
    public String cargarArchivo(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws DatosInvalidosCrearCargaOfertaException, DatosInvalidosCrearDistribucionViandasException, DatosInvalidosCrearRPESVException, DatosInvalidosCrearHCHException, FilaDeDatosIncompletaException, DatosInvalidosCrearDonacionViandaException, IOException, URISyntaxException, DatosInvalidosCrearDonacionDineroException {
        try {
            migrador.migrar(file, false);

        } catch (FilaDeDatosIncompletaException e) {
            redirectAttributes.addFlashAttribute("messageAlert", e.getMessage());

            return "redirect:/admin";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("messageAlert", i18nService.getMessage("controller.AdminController.cargarArchivo_err"));

            return "redirect:/admin";
        }

        redirectAttributes.addFlashAttribute("messageSuccess", i18nService.getMessage("controller.AdminController.cargarArchivo"));

        return "redirect:/admin";
    }
}
