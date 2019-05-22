package com.geekhub.finalwork.service.export;

import com.geekhub.finalwork.service.export.excel.ExcelReport;
import com.geekhub.finalwork.service.export.excel.ExcelWorkerReport;
import com.geekhub.finalwork.service.export.pdf.PdfInstrumentAndMaterialReport;
import com.geekhub.finalwork.service.export.pdf.PdfWorkerReport;
import com.geekhub.finalwork.service.orders.dto.Order;
import com.geekhub.finalwork.service.orders.OrderService;
import com.geekhub.finalwork.service.orders.dto.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ExportController {
    private final OrderService orderService;

    @Autowired
    public ExportController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/excelWorkers", method = RequestMethod.GET)
    public ModelAndView getExcelOfWorkers() {
        List<Worker> workers = orderService.getAllWorker();
        return new ModelAndView(new ExcelWorkerReport(), "workerList", workers);
    }

    @RequestMapping(value = "/excelInstruments", method = RequestMethod.GET)
    public ModelAndView getExcelOfInstruments() {
        List<Order> instruments = orderService.getAllInstrument();
        return new ModelAndView(new ExcelReport("instruments", "instrumentList", "Instrument Data", "instrument"), "instrumentList", instruments);
    }

    @RequestMapping(value = "/excelMaterials", method = RequestMethod.GET)
    public ModelAndView getExcelOfMaterials() {
        List<Order> materials = orderService.getAllMaterial();
        return new ModelAndView(new ExcelReport("materials", "materialList", "Material Data", "material"), "materialList", materials);
    }

    @RequestMapping(value = "/pdfWorkers", method = RequestMethod.GET)
    public ModelAndView getPdfOfWorkers() {
        List<Worker> workers = orderService.getAllWorker();
        return new ModelAndView(new PdfWorkerReport(), "workerList", workers);
    }

    @RequestMapping(value = "/pdfInstruments", method = RequestMethod.GET)
    public ModelAndView getPdfOfInstruments() {
        List<Order> instruments = orderService.getAllInstrument();
        return new ModelAndView(new PdfInstrumentAndMaterialReport("instruments", "instrumentList", "Instruments", "instruments"), "instrumentList", instruments);
    }

    @RequestMapping(value = "/pdfMaterials", method = RequestMethod.GET)
    public ModelAndView getPdfOfMaterials() {
        List<Order> materials = orderService.getAllMaterial();
        return new ModelAndView(new PdfInstrumentAndMaterialReport("materials", "materialList", "Materials", "materials"), "materialList", materials);
    }
}