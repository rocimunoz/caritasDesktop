package com.reparadoras.caritas.ui.utils;


import java.time.format.DateTimeFormatter;


public class PdfExporter
{

    // private static final Log LOG = LogFactory.getLog(PdfExporter.class);

    private static final String PDF_EXTENSION = ".pdf";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private static final String OBSERVATION_TYPES_SEPARATOR = "|";
    private static final float DOCUMENT_MARGIN_LEFT = 36;
    private static final float DOCUMENT_MARGIN_RIGHT = 36;
    private static final float DOCUMENT_MARGIN_TOP = 10;
    private static final float DOCUMENT_MARGIN_BOTTOM = 50;
    private static final String TABLE_HEADER_BACKGROUND_COLOR = "#E2001A";
    private static final String TABLE_CELL_HEADER_BACKGROUND_COLOR = "#EDEDED";
    private static final float MINIMUN_HEIGHT_CELLS = 20;
    private static final float SPACING_BEFORE_TABLE = 10;
    private static final float SPACING_AFTER_TABLE = 20;
    private static final int TABLES_COL_NUMBER = 4;
    private static final int RESOLUTION_DATA_TABLE_COL_NUMBER = 2;
    // private static final Font TITLE_FONT = new Font(FontFamily.HELVETICA, 20,
    // Font.NORMAL);
    // private static final Font TABLE_HEADER_FONT = new
    // Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
    // private static final Font TABLE_CELL_HEADER_FONT = new
    // Font(FontFamily.HELVETICA, 9, Font.BOLD);
    // private static final Font TABLE_CELL_FONT = new
    // Font(FontFamily.HELVETICA, 9, Font.NORMAL);

    /*
     * public File export(Inspection inspection) throws DocumentException,
     * IOException { HeaderFooterPageEvent event = new HeaderFooterPageEvent();
     * Document document = new Document(PageSize.A4, DOCUMENT_MARGIN_LEFT,
     * DOCUMENT_MARGIN_RIGHT, DOCUMENT_MARGIN_TOP + event.getTableHeight(),
     * DOCUMENT_MARGIN_BOTTOM); PdfWriter pdfWriter = null; File result = null;
     * FileOutputStream fileOut = null; result = new
     * File(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +
     * "/temp/" + inspection.getOperationCode() + PDF_EXTENSION); fileOut = new
     * FileOutputStream(result); pdfWriter = PdfWriter.getInstance(document,
     * fileOut); pdfWriter.setPageEvent(event); document.open();
     * document.add(new
     * Paragraph(I18NUtil.getI18N().get("operations.pdf.inspection.title"),
     * TITLE_FONT)); printInspectionHeaderTable(document, inspection);
     * printResolutionDataTable(document, inspection); if(inspection instanceof
     * SecurityInspection) { printValorationForm(document, (SecurityInspection)
     * inspection); } printWorkers(document, inspection);
     * printObservationsInspector(document, inspection);
     * printObservationsWorkBoss(document, inspection);
     * if(!inspection.getIncidences().isEmpty()) {
     * printIncidencesTable(document, inspection); } document.close();
     * fileOut.close(); return result; } private void
     * printInspectionHeaderTable(Document document, Inspection inspection)
     * throws DocumentException { JobHealthSafety jobHealthSafety =
     * jobHealthSafetyService.getJobHealthSafetyProxiesInitialized(inspection.
     * getJob().getId()); HealthSafetyUser healthSafetyUser =
     * healthSafetyUserService.getHealthSafetyUser(jobHealthSafety.getUser().
     * getId()); PdfPTable table = createTable(TABLES_COL_NUMBER);
     * table.setSpacingBefore(SPACING_BEFORE_TABLE);
     * table.setSpacingAfter(SPACING_AFTER_TABLE);
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header"), TABLES_COL_NUMBER));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.code")));
     * table.addCell(createCellParagraph(inspection.getOperationCode()));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.inspector")));
     * table.addCell(createCellParagraph(healthSafetyUser.getName() + " " +
     * healthSafetyUser.getSurname()));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.creation.date")));
     * table.addCell(createCellParagraph(jobHealthSafety.getCreationDate().
     * format(FORMATTER)));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.direction")));
     * table.addCell(createCellParagraph(jobHealthSafety.getDirection()));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.general.direction")));
     * table.addCell(createCellParagraph(jobHealthSafety.getGeneralDirection().
     * getName())); table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.area")));
     * table.addCell(createCellParagraph(jobHealthSafety.getArea().getName()));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.business.unit")));
     * table.addCell(createCellParagraph(jobHealthSafety.getBusinessUnit().
     * getName())); table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.job.center")));
     * table.addCell(createCellParagraph(jobHealthSafety.getJobCenter()));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.work")));
     * table.addCell(createCellParagraph(jobHealthSafety.getWork() != null ?
     * jobHealthSafety.getWork().getName() : ""));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.contract")));
     * table.addCell(createCellParagraph(jobHealthSafety.getContract() != null ?
     * jobHealthSafety.getContract().getName() : ""));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.contractor")));
     * table.addCell(createCellParagraph(jobHealthSafety.getContractor().getName
     * ())); table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.inspector.contractor")));
     * table.addCell(createCellParagraph(jobHealthSafety.getInspectorContractor(
     * ) != null ? jobHealthSafety.getInspectorContractor().getName() : ""));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.framework.contract"))); PdfPCell cell =
     * createCellParagraph(jobHealthSafety.getFrameworkContract() ?
     * I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.framework.contract.yes") :
     * I18NUtil.getI18N().get(
     * "operations.pdf.inspection.header.framework.contract.no"));
     * cell.setColspan(TABLES_COL_NUMBER - 1); table.addCell(cell);
     * document.add(table); } private void printResolutionDataTable(Document
     * document, Inspection inspection) throws DocumentException {
     * JobHealthSafety jobHealthSafety =
     * jobHealthSafetyService.getJobHealthSafetyProxiesInitialized(inspection.
     * getJob().getId()); PdfPTable table =
     * createTable(RESOLUTION_DATA_TABLE_COL_NUMBER); table.setWidths(new
     * float[] { 1, 3 }); table.setSpacingAfter(SPACING_AFTER_TABLE);
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.resolution.data.header"),
     * RESOLUTION_DATA_TABLE_COL_NUMBER));
     * if(!StringUtils.isBlank(inspection.getObservationType())) {
     * StringTokenizer tokenizer = new
     * StringTokenizer(inspection.getObservationType(),
     * OBSERVATION_TYPES_SEPARATOR); while (tokenizer.hasMoreElements()) {
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.resolution.data.observation.type")));
     * table.addCell(createCellParagraph(tokenizer.nextToken())); } }
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.resolution.data.observations")));
     * table.addCell(createCellParagraph(jobHealthSafety.getObservation()));
     * document.add(table); } private void printValorationForm(Document
     * document, SecurityInspection securityInspection) throws DocumentException
     * { PdfPTable table = createTable(RESOLUTION_DATA_TABLE_COL_NUMBER);
     * table.setWidths(new float[] { 4, 1 });
     * table.setSpacingAfter(SPACING_AFTER_TABLE);
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.valoration.form.header"),
     * RESOLUTION_DATA_TABLE_COL_NUMBER)); for (QuestionsGroup questionsGroup :
     * answerValueUtils.getQuestionsGroupFromOperationAnswerValues(
     * securityInspection)) { PdfPCell cell =
     * createCellDataHeader(questionsGroup.getName());
     * cell.setColspan(RESOLUTION_DATA_TABLE_COL_NUMBER); table.addCell(cell);
     * for (Question question :
     * answerValueUtils.getQuestionsFromQuestionsGroupAnswerValues(
     * questionsGroup)) {
     * table.addCell(createCellParagraph(question.getStatement()));
     * table.addCell(createCellParagraph(answerValueUtils.
     * getSelectedAnswerFromAnswerValue(question,
     * securityInspection).getTitle())); } } document.add(table); } private void
     * printWorkers(Document document, Inspection inspection) throws
     * DocumentException { PdfPTable table = createTable(TABLES_COL_NUMBER);
     * table.setSpacingAfter(SPACING_AFTER_TABLE);
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.workers.header"), TABLES_COL_NUMBER)); for
     * (Worker worker : inspection.getWorkers()) { Worker initializedWorker =
     * workerService.getWorkerProxiesInitialized(worker.getId()); PdfPCell cell
     * = createCellDataHeader(initializedWorker.getName().toUpperCase());
     * cell.setColspan(TABLES_COL_NUMBER); table.addCell(cell);
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.workers.dni")));
     * table.addCell(createCellParagraph(initializedWorker.getNif()));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.workers.contractor")));
     * table.addCell(createCellParagraph(initializedWorker.getContractor().
     * getName())); table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.workers.worker.type"))); cell =
     * createCellParagraph(initializedWorker.getWorkerType() != null ?
     * initializedWorker.getWorkerType().getName() : "");
     * cell.setColspan(TABLES_COL_NUMBER - 1); table.addCell(cell); }
     * document.add(table); } private void printObservationsInspector(Document
     * document, Inspection inspection) throws DocumentException { PdfPTable
     * table = createTable(RESOLUTION_DATA_TABLE_COL_NUMBER);
     * table.setWidths(new float[] { 1, 3 });
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.observations.inspector.header"),
     * RESOLUTION_DATA_TABLE_COL_NUMBER)); printObservationsInTable(table,
     * getPreviousObservations(inspection),
     * I18NUtil.getI18N().get("operations.pdf.inspection.previous.observations")
     * ); printObservationsInTable(table, getGoodPractices(inspection),
     * I18NUtil.getI18N().get("operations.pdf.inspection.good.practices"));
     * printObservationsInTable(table, getInspectorObservations(inspection),
     * I18NUtil.getI18N().get("operations.pdf.inspection.inspector.observations"
     * )); table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.signature"))); try {
     * table.addCell(getImageCell(imageUtils.getImageFromAlfresco(inspection.
     * getStartUserSignature()))); } catch (Exception e) {
     * LOG.error(e.getMessage()); } document.add(table); } private void
     * printObservationsWorkBoss(Document document, Inspection inspection)
     * throws DocumentException { PdfPTable table =
     * createTable(RESOLUTION_DATA_TABLE_COL_NUMBER);
     * table.setSpacingAfter(SPACING_AFTER_TABLE); table.setWidths(new float[] {
     * 1, 3 }); table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.observations.work.boss.header"),
     * RESOLUTION_DATA_TABLE_COL_NUMBER)); printObservationsInTable(table,
     * getWorkBossObservations(inspection), I18NUtil.getI18N().get(
     * "operations.pdf.inspection.previous.inspector.observations.work.boss"));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.signature"))); OperationWorkerSignatureFilter
     * operationWorkerSignatureFilter = new OperationWorkerSignatureFilter();
     * operationWorkerSignatureFilter.setOperationId(inspection.getId()); try {
     * table.addCell(getImageCell(imageUtils.getImageFromAlfresco(
     * operationWorkerSignatureService.getOperationWorkerSignatures(
     * operationWorkerSignatureFilter, null).getItem(0,
     * Boolean.TRUE).getStartSignature()))); } catch (Exception e) {
     * LOG.error(e.getMessage()); } document.add(table); } private void
     * printIncidencesTable(Document document, Inspection inspection) throws
     * DocumentException { PdfPTable table = createTable(TABLES_COL_NUMBER);
     * table.setSpacingAfter(SPACING_AFTER_TABLE);
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.incidences.header"), TABLES_COL_NUMBER)); for
     * (Incidence incidence : inspection.getIncidences()) {
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.incidences.code")));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.incidences.state")));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.incidences.type")));
     * table.addCell(createCellDataHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.incidences.creation.date")));
     * table.addCell(createCellParagraph(incidence.getCode()));
     * table.addCell(createCellParagraph(incidence.getState()));
     * table.addCell(createCellParagraph(incidence.getIncidenceType()));
     * table.addCell(createCellParagraph(incidence.getCreationDate().format(
     * FORMATTER))); PdfPCell cell =
     * createCellParagraph(incidence.getDescription());
     * cell.setColspan(TABLES_COL_NUMBER); table.addCell(cell);
     * table.addCell(createCellHeader(I18NUtil.getI18N().get(
     * "operations.pdf.inspection.incidences.images.header"),
     * TABLES_COL_NUMBER)); for (IncidencePicture incidencePicture :
     * incidencePictureService.getIncidencePictures(incidence).getItems()) {
     * PdfPCell imageCell = null; try { imageCell =
     * getImageCell(imageUtils.getImageFromAlfresco(incidencePicture.getPicture(
     * ))); } catch (Exception e) { LOG.error(e.getMessage()); } if(imageCell ==
     * null) { imageCell = new PdfPCell(); }
     * imageCell.setColspan(TABLES_COL_NUMBER); table.addCell(imageCell); } }
     * document.add(table); } private PdfPCell getImageCell(byte[]
     * imageByteArray) throws IOException, BadElementException { Image image =
     * Image.getInstance(imageUtils.getResizedImageByteArrayFromByteArray(
     * imageByteArray)); image.scalePercent(70); Paragraph p = new Paragraph();
     * p.add(new Chunk(image, 0, 0, true)); return new PdfPCell(p); } private
     * void printObservationsInTable(PdfPTable table, List<Observation>
     * observations, String title) { for (int i = 0; i < observations.size();
     * i++) { if(i == 0) { table.addCell(createCellDataHeader(title)); } else {
     * table.addCell(createCellDataHeader("")); }
     * table.addCell(createCellParagraph(observations.get(i).getObservation()));
     * } } private List<Observation> getPreviousObservations(Inspection
     * inspection) { ObservationFilter observationFilter = new
     * ObservationFilter();
     * observationFilter.setOperationId(inspection.getId());
     * observationFilter.setObservationTypes(ObservationTypes.
     * PREVIOUS_OBSERVATION); return
     * observationService.getObservationsProxiesInitialized(observationFilter,
     * null).getItems(); } private List<Observation> getGoodPractices(Inspection
     * inspection) { ObservationFilter observationFilter = new
     * ObservationFilter();
     * observationFilter.setOperationId(inspection.getId());
     * observationFilter.setObservationTypes(ObservationTypes.GOOD_PRACTISES);
     * return
     * observationService.getObservationsProxiesInitialized(observationFilter,
     * null).getItems(); } private List<Observation>
     * getInspectorObservations(Inspection inspection) { ObservationFilter
     * observationFilter = new ObservationFilter();
     * observationFilter.setOperationId(inspection.getId());
     * observationFilter.setObservationTypes(ObservationTypes.
     * OBSERVATIONS_INSPECTOR); return
     * observationService.getObservationsProxiesInitialized(observationFilter,
     * null).getItems(); } private List<Observation>
     * getWorkBossObservations(Inspection inspection) { ObservationFilter
     * observationFilter = new ObservationFilter();
     * observationFilter.setOperationId(inspection.getId());
     * observationFilter.setObservationTypes(ObservationTypes.OBSERVATIONS_BOSS)
     * ; return
     * observationService.getObservationsProxiesInitialized(observationFilter,
     * null).getItems(); } private PdfPTable createTable(Integer colNumber) {
     * PdfPTable table = new PdfPTable(colNumber);
     * table.setWidthPercentage(100); table.setTableEvent(new
     * TableBorderEvent()); return table; } private PdfPCell
     * createCellHeader(String title, Integer colspan) { PdfPCell cell = new
     * PdfPCell(new Phrase(title, TABLE_HEADER_FONT));
     * cell.setMinimumHeight(MINIMUN_HEIGHT_CELLS); cell.setColspan(colspan);
     * cell.setVerticalAlignment(Element.ALIGN_TOP);
     * cell.setBackgroundColor(getBaseColorFromHex(TABLE_HEADER_BACKGROUND_COLOR
     * )); return cell; } private PdfPCell createCellParagraph(String text) {
     * PdfPCell cell = new PdfPCell(new Phrase(text, TABLE_CELL_FONT));
     * cell.setVerticalAlignment(Element.ALIGN_TOP); cell.setMinimumHeight(20);
     * return cell; } private PdfPCell createCellDataHeader(String title) {
     * PdfPCell cell = new PdfPCell(new Phrase(title, TABLE_CELL_HEADER_FONT));
     * cell.setVerticalAlignment(Element.ALIGN_TOP); cell.setMinimumHeight(20);
     * cell.setBackgroundColor(getBaseColorFromHex(
     * TABLE_CELL_HEADER_BACKGROUND_COLOR)); return cell; } private BaseColor
     * getBaseColorFromHex(String hexColor) { Integer r =
     * Integer.valueOf(hexColor.substring(1, 3), 16); Integer g =
     * Integer.valueOf(hexColor.substring(3, 5), 16); Integer b =
     * Integer.valueOf(hexColor.substring(5, 7), 16); return new BaseColor(r, g,
     * b); }
     */
}
