<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

  <!-- ========================= -->
  <!-- SWIMMERS                  -->
  <!-- ========================= -->
  <xsl:template match="swimmers">

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="16pt" font-weight="bold" space-after="5mm">ALL SWIMMERS</fo:block>
          <fo:block font-size="13pt">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate">
              <fo:table-column column-width="5cm"/>
              <fo:table-column column-width="3cm"/>
              <fo:table-column column-width="7cm"/>
              <fo:table-header>
                <fo:table-row background-color="#ccc">
                  <fo:table-cell ><fo:block>NAME</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>DOB</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>SQUAD</fo:block></fo:table-cell>
                </fo:table-row>
              </fo:table-header>
              <fo:table-body>
                <xsl:apply-templates select="swimmer"/>
              </fo:table-body>
            </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

  <!-- ========================= -->
  <!-- SWIMMER                   -->
  <!-- ========================= -->
  <xsl:template match="swimmer">
    <xsl:element name="fo:table-row">
      <fo:table-cell>
        <fo:block><xsl:value-of select="name"/></fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block><xsl:value-of select="dob"/></fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block><xsl:value-of select="squad"/></fo:block>
      </fo:table-cell>
    </xsl:element>
  </xsl:template>

</xsl:stylesheet>
