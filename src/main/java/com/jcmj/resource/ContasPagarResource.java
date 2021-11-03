package com.jcmj.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.jcmj.domain.ContasPagar;
import com.jcmj.domain.dto.ContasPagarDTO;
import com.jcmj.service.ContasPagarService;

@RestController
@RequestMapping(value = "/contas")
public class ContasPagarResource {	
	
	@Autowired
    ContasPagarService service;	
	
	@PostMapping
	public ResponseEntity<ContasPagarDTO> create(@Valid @RequestBody ContasPagarDTO objDto){
		
		List<ContasPagar> contasSalvas = service.insertComParcelas(objDto);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(contasSalvas.get(0).getNd())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	@PutMapping
	public ResponseEntity<ContasPagarDTO> update(@Valid @RequestBody ContasPagarDTO objDto){
		List<ContasPagar> contasSalvas = service.insertComParcelas(objDto);
		URI uri =  ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(contasSalvas.get(0).getNd())
				.toUri();
		return ResponseEntity.created(uri).build();
	}
	@GetMapping
	public ResponseEntity<List<ContasPagar>> findAll(){		
		List<ContasPagar> list = service.findAll();
		List<ContasPagarDTO> result = list.stream().map(obj -> new ContasPagarDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ContasPagarDTO> delete(@PathVariable Integer id){
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	
	
	
	
    
	/*
	 * //Mudando a forma de cadastro para ajax
	 * 
	 * @GetMapping("/update-ajax/{id}") public String updateAjax(@PathVariable("id")
	 * Integer id, ModelMap model) { ContasPagar conta = service.find(id);
	 * model.addAttribute("conta",conta); return "paginas/cn/financeiro/apagar-add";
	 * }
	 * 
	 * @PostMapping("/ajax/save") public ResponseEntity<?> salvando(@Valid
	 * ContasPagar conta, BindingResult result){
	 * 
	 * if(result.hasErrors()){ Map<String, String> errors = new HashMap<>(); for
	 * (FieldError error : result.getFieldErrors()) {
	 * errors.put(error.getField(),error.getDefaultMessage()); } return
	 * ResponseEntity.unprocessableEntity().body(errors); }
	 * 
	 * service.insertComParcelas(conta); return ResponseEntity.ok().build(); }
	 * 
	 * @ModelAttribute("empresas") public List<Empresa> getCategorias(){ return
	 * empresaService.findAll(); }
	 * 
	 * @ModelAttribute("situacao") public Situacao[] getSituacao(){ return
	 * Situacao.values(); }
	 * 
	 * @ModelAttribute("formaPagamento") public FormaPagamento[]
	 * getFormasPagamentos(){ return FormaPagamento.values(); }
	 * 
	 * @ModelAttribute("tiposDespesa") public TipoDespesa[] getTipoDespesa(){ return
	 * TipoDespesa.values(); }
	 * 
	 * @GetMapping("/ajax/add") public String abrirCadastro(ModelMap model){
	 * model.addAttribute("conta", new ContasPagar()); return
	 * "paginas/cn/financeiro/apagar-add"; }
	 * 
	 * @RequestMapping("/buscar-por-numero-documento") public ModelAndView
	 * buscarPorNumeroDocumento(@RequestParam("numeroDocumento") String
	 * numeroDocumento ){
	 * 
	 * Boolean isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName();
	 * if(autori.contains("diretor") || autori.contains("admin")){ isTop = true; }
	 * 
	 * List<ContasPagar> contas = service.findByNumeroDocumento(numeroDocumento);
	 * ModelAndView model = new
	 * ModelAndView("paginas/cn/financeiro/apagar-buscar-nd");
	 * model.addObject("contas", contas); model.addObject("isTop", isTop); return
	 * model;
	 * 
	 * }
	 * 
	 * @GetMapping("/utilitarioCadastro-ajax") public ResponseEntity<?>
	 * cadastroViaAjax(@RequestParam("id") Integer id,
	 * 
	 * @RequestParam("parametro") String parametro,
	 * 
	 * @RequestParam("tipo") String tipo) throws Exception {
	 * 
	 * service.cadastroUtil(id, parametro, tipo); return
	 * ResponseEntity.ok().build(); }
	 * 
	 * @GetMapping("/autorizacao") public ResponseEntity<?>
	 * autorizarPagamento(@RequestParam("id") Integer id){
	 * 
	 * service.autorizarPagamento(id); return ResponseEntity.ok().build(); }
	 * 
	 * 
	 * // =====================fim ajax===========================
	 * 
	 * @RequestMapping("/delete") public ResponseEntity<?>
	 * delete(@RequestParam("id") Integer id) {
	 * 
	 * service.delete(id); return ResponseEntity.ok().build(); }
	 * 
	 * @RequestMapping("todas-contas") public ModelAndView
	 * findAll(@RequestParam(defaultValue = "0") int pagina,
	 * 
	 * @RequestParam(defaultValue = "30") int porPagina,
	 * 
	 * @RequestParam(defaultValue = "dataVencimento") String ordenacao,
	 * 
	 * @RequestParam(defaultValue = "ASC") Sort.Direction direcao){ Boolean isTop =
	 * false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName(); if
	 * (autori.contains("diretor") || autori.contains("admin")) { isTop = true; }
	 * 
	 * List<ContasPagar> contas = filtro.buscandoContasDoMesAtual();
	 * 
	 * BigDecimal totalApagarDia = service.calcularValorTotalDoDia(contas);
	 * BigDecimal totalCalculadoDoMes = service.calcularValorTotalDoMes(contas);
	 * BigDecimal totalPagodoMes = service.calculandoTotalPago(contas); BigDecimal
	 * totalPagoDoDia = service.calculandoTotalPagoDoDia(contas); BigDecimal
	 * totalApagarSemana = service.calculandoApagarSemana(contas); BigDecimal
	 * totalPagoSemana = service.calcularTotalPagoSemana(contas);
	 * 
	 * BigDecimal pendenteMes = totalPagodoMes.subtract(totalCalculadoDoMes);
	 * BigDecimal pendenteSemana = totalPagoSemana.subtract(totalApagarSemana) ;
	 * BigDecimal pendenteHoje = totalPagoDoDia.subtract(totalApagarDia) ;
	 * 
	 * ModelAndView model = new ModelAndView("paginas/financeiro/lista-a-pagar");
	 * model.addObject("contas", contas); model.addObject("isTop", isTop);
	 * model.addObject("totalApagarDia", totalApagarDia );
	 * model.addObject("totalCalculadoDoMes", totalCalculadoDoMes );
	 * model.addObject("totalPagoDoDia", totalPagoDoDia );
	 * model.addObject("pendenteHoje", pendenteHoje );
	 * model.addObject("totalApagarSemana", totalApagarSemana );
	 * model.addObject("totalPagoSemana", totalPagoSemana );
	 * model.addObject("pendenteSemana", pendenteSemana );
	 * model.addObject("totalPagodoMes", totalPagodoMes );
	 * model.addObject("pendenteMes", pendenteMes );
	 * 
	 * return model; }
	 * 
	 * @RequestMapping("/contas-filtro") public ModelAndView
	 * findAllFiltrado(@RequestParam("parametro") String parametro,
	 * 
	 * @RequestParam("campo") String campo) { Boolean isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName(); if
	 * (autori.contains("diretor") || autori.contains("admin")) { isTop = true; }
	 * List<ContasPagar> contas = filtro.findAllFiltrado(parametro, campo);
	 * BigDecimal totalPago = service.calculandoTotalPago(contas); BigDecimal
	 * totalDuplicatas = service.calcularValorTotalDuplicata(contas); BigDecimal
	 * pendente = totalPago.subtract(totalDuplicatas) ;
	 * 
	 * ModelAndView model = new ModelAndView("paginas/financeiro/lista-a-pagar");
	 * model.addObject("contas", contas); model.addObject("isTop", isTop);
	 * model.addObject("totalPago", totalPago); model.addObject("totalDuplicatas",
	 * totalDuplicatas); model.addObject("pendente", pendente );
	 * 
	 * 
	 * return model; }
	 * 
	 * @RequestMapping("/contas-filtro-avancado") public ModelAndView
	 * findAllFiltradoAvancado(@RequestParam("empresa") String empresa,
	 * 
	 * @RequestParam("dataInicial") String dataInicial, @RequestParam("dataFinal")
	 * String dataFinal,
	 * 
	 * @RequestParam("situacao") String situacao, @RequestParam("tipoDespesa")
	 * String tipoDespesa,@RequestParam("forma") String forma) throws ParseException
	 * { Boolean isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName();
	 * if(autori.contains("diretor") || autori.contains("admin")){ isTop = true; }
	 * List<ContasPagar>contas = filtro.findAllFiltradoAvancado(empresa,
	 * dataInicial,dataFinal,tipoDespesa, situacao,forma); BigDecimal totalPago =
	 * service.calculandoTotalPago(contas); BigDecimal totalDuplicatas =
	 * service.calcularValorTotalDuplicata(contas); BigDecimal pendente =
	 * totalPago.subtract(totalDuplicatas); ModelAndView model = new
	 * ModelAndView("paginas/financeiro/lista-a-pagar"); model.addObject("contas",
	 * contas); model.addObject("isTop", isTop); model.addObject("totalPago",
	 * totalPago); model.addObject("totalDuplicatas", totalDuplicatas);
	 * model.addObject("pendente", pendente ); return model; }
	 * 
	 * @RequestMapping("/contas-filtro-empresa-data") public ModelAndView
	 * findAllFiltradoAvancadoEmpresaAndData(@RequestParam("empresa") String
	 * empresa,
	 * 
	 * @RequestParam("dataInicial") String dataInicial, @RequestParam("dataFinal")
	 * String dataFinal) throws ParseException { Boolean isTop = false; String
	 * autori = SecurityContextHolder.getContext().getAuthentication().getName();
	 * if(autori.contains("diretor") || autori.contains("admin")){ isTop = true; }
	 * List<ContasPagar>contas =
	 * filtro.findAllFiltradoAvancadoEmpresaAndData(empresa, dataInicial,dataFinal);
	 * BigDecimal totalPago = service.calculandoTotalPago(contas); BigDecimal
	 * totalDuplicatas = service.calcularValorTotalDuplicata(contas); BigDecimal
	 * pendente = totalPago.subtract(totalDuplicatas); ModelAndView model = new
	 * ModelAndView("paginas/financeiro/lista-a-pagar"); model.addObject("contas",
	 * contas); model.addObject("isTop", isTop); model.addObject("totalPago",
	 * totalPago); model.addObject("totalDuplicatas", totalDuplicatas);
	 * model.addObject("pendente", pendente ); return model; }
	 * 
	 * @RequestMapping("/contas-filtro-periodo") public ModelAndView
	 * findAllFiltradoAvancadoPorPeriodo(@RequestParam("dataInicial") String
	 * dataInicial, @RequestParam("dataFinal") String dataFinal) throws
	 * ParseException { Boolean isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName();
	 * if(autori.contains("diretor") || autori.contains("admin")){ isTop = true; }
	 * List<ContasPagar>contas =
	 * filtro.findAllFiltradoAvancadoPeriodo(dataInicial,dataFinal); BigDecimal
	 * totalPago = service.calculandoTotalPago(contas); BigDecimal totalDuplicatas =
	 * service.calcularValorTotalDuplicata(contas); BigDecimal pendente =
	 * totalDuplicatas.subtract(totalPago) ; ModelAndView model = new
	 * ModelAndView("paginas/financeiro/lista-a-pagar"); model.addObject("contas",
	 * contas); model.addObject("isTop", isTop); model.addObject("totalPago",
	 * totalPago); model.addObject("totalDuplicatas", totalDuplicatas);
	 * model.addObject("pendente", pendente ); return model; }
	 * 
	 * @RequestMapping("/contas-filtro-data-pagamento") public ModelAndView
	 * findAllFiltradoAvancadoPorDataPagamento(@RequestParam("dataInicial") String
	 * dataInicial, @RequestParam("dataFinal") String dataFinal) throws
	 * ParseException { Boolean isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName();
	 * if(autori.contains("diretor") || autori.contains("admin")){ isTop = true; }
	 * List<ContasPagar>contas =
	 * filtro.findAllFiltradoAvancadoDataPagamento(dataInicial,dataFinal);
	 * BigDecimal totalPago = service.calculandoTotalPago(contas); BigDecimal
	 * totalDuplicatas = service.calcularValorTotalDuplicata(contas); BigDecimal
	 * pendente = totalDuplicatas.subtract(totalPago) ; ModelAndView model = new
	 * ModelAndView("paginas/financeiro/lista-a-pagar"); model.addObject("contas",
	 * contas); model.addObject("isTop", isTop); model.addObject("totalPago",
	 * totalPago); model.addObject("totalDuplicatas", totalDuplicatas);
	 * model.addObject("pendente", pendente ); return model; }
	 * 
	 * 
	 * @GetMapping("/aprova-ou-desaprova-compras/{id}") public ModelAndView
	 * aprovarCompras(@RequestParam("id") Integer id, RedirectAttributes attributes)
	 * throws ObjectNotFoundException {
	 * 
	 * service.criandoDespesaApartirDeCompras(id);
	 * attributes.addFlashAttribute("mensagem","A pagar cadastrado com sucesso!");
	 * return new ModelAndView("redirect:/apagar/todas-contas/" ); }
	 * 
	 * @GetMapping("/para-aprovacao") public ModelAndView paraAprovacao(){
	 * List<Fornecedor> listFornecedores =
	 * fornecedorService.findByIsEnviarParaFinanceiroAndIsAprovadoPeloFinanceiro(
	 * true, false); ModelAndView model = new
	 * ModelAndView("paginas/financeiro/aguardando-aprovacao"); Double total =0.0;
	 * for (Fornecedor fornecedor : listFornecedores) { total +=
	 * fornecedor.getValorTotalCompra(); } model.addObject("listaFornecedores",
	 * listFornecedores); model.addObject("total", total); return model; }
	 * 
	 * @GetMapping("/confirmar-a-pagar/") public ModelAndView
	 * enviarFornecedorParaAPagar(@RequestParam("id") Integer id, RedirectAttributes
	 * attributes) throws ObjectNotFoundException {
	 * 
	 * Fornecedor fornecedor = fornecedorService.find(id);
	 * fornecedor.setIsAprovadoPeloFinanceiro(true);
	 * fornecedorService.insert(fornecedor);
	 * 
	 * ContasPagar novaConta = new ContasPagar();
	 * novaConta.setFornecedor(fornecedor.getNome()+"/"+fornecedor.getMarca());
	 * novaConta.setObservacao(fornecedor.getId().toString());
	 * novaConta.setSituacao(Situacao.PENDENTE.getDescricao()); //
	 * novaConta.setValorDuplicata(fornecedor.getValorTotalCompra());
	 * novaConta.setFormaPagamento(FormaPagamento.BOLETO.getDescricao());
	 * novaConta.setTipoDespesa(TipoDespesa.VARIAVEL.getDescricao());
	 * novaConta.setEmpresa(empresaService.findByNome("CN Muzema"));
	 * novaConta.setDataVencimento(fornecedor.getPrevisaoEntrega());
	 * service.insert(novaConta);
	 * attributes.addFlashAttribute("mensagem","A pagar cadastrado com sucesso!");
	 * return new ModelAndView("redirect:/apagar/todas-contas/" ); }
	 * 
	 * @RequestMapping("/preparar-upload") public ModelAndView prepararUpload(){
	 * 
	 * return new ModelAndView("paginas/financeiro/carregamento"); }
	 * 
	 * @PostMapping("/upload") public String uploadFile(@RequestParam("file")
	 * MultipartFile file, RedirectAttributes attributes) throws ParseException {
	 * 
	 * service.deletandoArquivoDoDiretorio(); // check if file is empty if
	 * (file.isEmpty()) { attributes.addFlashAttribute("message",
	 * "Please select a file to upload."); return "redirect:/apagar/todas-contas"; }
	 * 
	 * // normalize the file path String fileName =
	 * StringUtils.cleanPath(file.getOriginalFilename());
	 * 
	 * // save the file on the local file system try { Path path =
	 * Paths.get(UPLOAD_DIR + fileName); Files.copy(file.getInputStream(), path,
	 * StandardCopyOption.REPLACE_EXISTING); attributes.addFlashAttribute("message",
	 * "Carregamento realizado com sucesso"); } catch (IOException e) {
	 * e.printStackTrace(); attributes.addFlashAttribute("message", e.getMessage());
	 * return "redirect:/apagar/todas-contas"; }
	 * 
	 * // return success response attributes.addFlashAttribute("message",
	 * "Carregamento realizado com sucesso " + fileName + '!');
	 * service.pegandoDiretorioParaRenomearArquivo(); service.pegaMaterials();
	 * return "redirect:/apagar/todas-contas"; }
	 * 
	 * @GetMapping(value="/alimentar") public ModelAndView
	 * carregarListaNoSistema(RedirectAttributes attributes) throws ParseException {
	 * 
	 * service.pegaMaterials(); ModelAndView mv = new
	 * ModelAndView("paginas/etiqueta/etiquetas");
	 * attributes.addFlashAttribute("mensagem"," Banco de dados carregado!"); return
	 * mv;
	 * 
	 * }
	 * 
	 * @GetMapping(value="/relatorio") public void
	 * todasPromocao(@RequestParam("dataInicio") String dataInicio,
	 * 
	 * @RequestParam("dataFim") String dataFim, RedirectAttributes attributes,
	 * HttpServletRequest request, HttpServletResponse response) { Date
	 * dataInicioFormatada = new Date(); Date dataFimFormatada = new Date(); try {
	 * dataInicioFormatada = FormatadorUtil.simpleDateFormatDate(dataInicio);
	 * dataFimFormatada = FormatadorUtil.simpleDateFormatDate(dataFim); } catch
	 * (Exception e) {
	 * 
	 * }
	 * 
	 * PeriodoUtil periodo = new PeriodoUtil(dataInicioFormatada, dataFimFormatada);
	 * boolean isFlag = gerador.relatorioContasPagarPorPeriodo(periodo, context,
	 * request, response);
	 * 
	 * if(isFlag) { String fullPath =
	 * request.getServletContext().getRealPath("/resources/reports/"+"relatorio"+
	 * ".pdf"); fileDownload(fullPath, response, "relatorio.pdf"); }
	 * 
	 * } private void fileDownload(String fullPath, HttpServletResponse response,
	 * String filename) { File file = new File(fullPath); final int BUFFER_SIZE =
	 * 4096; if (file.exists()) {
	 * 
	 * try { FileInputStream inputStream = new FileInputStream(file); String myType
	 * = context.getMimeType(fullPath); response.setContentType(myType);
	 * response.setHeader("content-disposition", "attachment; filename=" +
	 * filename); OutputStream outputStream = response.getOutputStream();
	 * byte[]buffer = new byte[BUFFER_SIZE]; int bytesRead = -1; while((bytesRead =
	 * inputStream.read(buffer))!= -1) { outputStream.write(buffer, 0, bytesRead); }
	 * inputStream.close(); outputStream.close(); file.delete();
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } } }
	 * 
	 * 
	 * 
	 * @GetMapping(value = "ajuste-parcela") public ResponseEntity<?> ajusteParcela
	 * (@RequestParam("id") Integer id){
	 * 
	 * service.ajustarParcela(id);
	 * 
	 * return ResponseEntity.ok().build(); }
	 * 
	 * @GetMapping(value = "cadastro-parcela") public ResponseEntity<?>
	 * cadastroParcela (@RequestParam("id") Integer id, @RequestParam("parcela")
	 * String parcela){
	 * 
	 * service.cadastroParcela(id, parcela);
	 * 
	 * return ResponseEntity.ok().build(); }
	 * 
	 * @RequestMapping("/buscar-fornecedo-periodo") public ModelAndView
	 * findAllFornecedorPeriodo(@RequestParam("fornecedor") String fornecedor,
	 * 
	 * @RequestParam("dataInicio") String dataInicio,
	 * 
	 * @RequestParam("dataFim") String dataFim) throws ParseException { Boolean
	 * isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName();
	 * if(autori.contains("diretor") || autori.contains("admin")){ isTop = true; }
	 * List<ContasPagar>contas = filtro.findAllFornecedorPeriodo(fornecedor,
	 * dataInicio,dataFim); BigDecimal totalPago =
	 * service.calculandoTotalPago(contas); BigDecimal totalDuplicatas =
	 * service.calcularValorTotalDuplicata(contas); BigDecimal pendente =
	 * totalPago.subtract(totalDuplicatas); ModelAndView model = new
	 * ModelAndView("paginas/financeiro/lista-a-pagar"); model.addObject("contas",
	 * contas); model.addObject("isTop", isTop); model.addObject("totalPago",
	 * totalPago); model.addObject("totalDuplicatas", totalDuplicatas);
	 * model.addObject("pendente", pendente ); return model; }
	 * 
	 * @RequestMapping("/ajax-buscar-nd") public ModelAndView
	 * findAllPorNd(@RequestParam("numeroDocumento") String parametro) { Boolean
	 * isTop = false; String autori =
	 * SecurityContextHolder.getContext().getAuthentication().getName(); if
	 * (autori.contains("diretor") || autori.contains("admin")) { isTop = true; }
	 * List<ContasPagar> contas = filtro.findAllFiltradoPorNd(parametro); BigDecimal
	 * totalPago = service.calculandoTotalPago(contas); BigDecimal totalDuplicatas =
	 * service.calcularValorTotalDuplicata(contas); BigDecimal pendente =
	 * totalPago.subtract(totalDuplicatas) ;
	 * 
	 * ModelAndView model = new ModelAndView("paginas/financeiro/lista-a-pagar");
	 * model.addObject("contas", contas); model.addObject("isTop", isTop);
	 * model.addObject("totalPago", totalPago); model.addObject("totalDuplicatas",
	 * totalDuplicatas); model.addObject("pendente", pendente );
	 * 
	 * 
	 * return model; }
	 */
}
