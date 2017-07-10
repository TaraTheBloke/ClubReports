package com.accenture.swimmers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Collection;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

class PdfCreator {

    private static String SQUAD_XSL = "fop/squad.xsl";
    private static String SWIMMERS_XSL = "fop/swimmers.xsl";
    private static String FEES_XSL = "fop/fees.xsl";

    public static void createDocuments(Collection<Swimmer> swimmers) {
        createSwimmersDocumentFor(swimmers);
        createSquadsDocumentFor(Squad.squadsOf(swimmers));
        createFeesDocumentFor(Family.familiesOf(swimmers));
    }

    private static void createSwimmersDocumentFor(Collection<Swimmer> swimmers) {
        createPdf(xmlFor("swimmers", swimmers), "All Swimmers.pdf", SWIMMERS_XSL);
    }

    private static void createSquadsDocumentFor(Collection<Squad> squads) {
        createPdf(xmlFor("squads", squads), "All Squads.pdf", SQUAD_XSL);
    }

    private static void createFeesDocumentFor(Collection<Family> families) {
        createPdf(xmlFor("families", families), "Fees.pdf", FEES_XSL);
    }

    private static void createPdf(String xml, String pdfName, String xsl) {
        xml = removeUnsafeCharacters(xml);
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(pdfName))) {
            FopFactory fopFactory = FopFactory.newInstance();
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

            Transformer transformer = TransformerFactory.newInstance().newTransformer(new StreamSource(new File(xsl)));
            Source src = new StreamSource(new ByteArrayInputStream(xml.getBytes()));
            Result res = new SAXResult(fop.getDefaultHandler());

            transformer.transform(src, res);
        } catch (Exception e) {
            System.out.println("Failed to transform squad: " + xml);
            e.printStackTrace();
        }
    }

    private static String removeUnsafeCharacters(String xml) {
        return xml.replace("&", "&amp;");
    }

    private static String xmlFor(String rootName, Collection<? extends Xmlable> entities) {
        StringBuilder xml = new StringBuilder("<" + rootName + ">");
        entities.stream().forEach(entity -> xml.append(entity.toXml()));
        xml.append("</" + rootName + ">");
        return xml.toString();
    }
}
