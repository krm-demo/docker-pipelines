import groovy.xml.XmlParser

log.debug("phase = ${mojoExecution.lifecyclePhase}")
log.debug("plugin = ${mojoExecution.plugin}")
log.debug("goal = ${mojoExecution.goal}")
log.debug("execution = ${mojoExecution.executionId}")
log.debug("mojoSource = ${mojoExecution.source}")
log.debug("project.build.directory: '${project.build.directory}'")
log.debug("project.build.outputDirectory: '${project.build.outputDirectory}'")

XmlParser xmlParser = new XmlParser()
xmlParser.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false)
xmlParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
xmlParser.setFeature("http://xml.org/sax/features/namespaces", false)
xmlParser.setFeature("http://xml.org/sax/features/validation", false)
def jacocoXml = xmlParser.parse("${project.build.directory}/site/jacoco/jacoco.xml")
def logCounter(cnt) {
    String category = String.format("%-12s", cnt.@type.toLowerCase().capitalize())
    Integer missed = cnt.@missed as Integer
    Integer covered = cnt.@covered as Integer
    String percentage = String.format("%6.2f", 100 * covered / (covered + missed))
    String coveredStr = String.format("%4d", covered)
    String missedStr = String.format("%4d", missed)
    String totalStr = String.format("%4d", covered + missed)
    log.info("- ${category}: $percentage% - { covered = ${coveredStr} ; missed = $missedStr ; total = $totalStr }")
}
jacocoXml.'counter'.each { cnt -> logCounter(cnt) }
