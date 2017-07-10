<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

  <!-- ========================= -->
  <!-- FAMILIES                  -->
  <!-- ========================= -->
  <xsl:template match="families">

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>

      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="16pt" font-weight="bold" space-after="5mm">FAMILY FEES</fo:block>
          <fo:block font-size="10pt">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate">
              <fo:table-column column-width="4.8cm"/>
              <fo:table-column column-width="2.3cm"/>
              <fo:table-column column-width="2.3cm"/>
              <fo:table-column column-width="6.2cm"/>
              <fo:table-header>
                <fo:table-row background-color="#ccc">
                  <fo:table-cell ><fo:block>FAMILY NAME</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>FEES</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>MONTHLY</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>FEE STRUCTURE</fo:block></fo:table-cell>
                </fo:table-row>
              </fo:table-header>
              <fo:table-body>
                <xsl:apply-templates select="family"/>
              </fo:table-body>
            </fo:table>
          </fo:block>
          <fo:block font-size="12pt" font-weight="bold" space-before="3mm">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate">
              <fo:table-column column-width="7cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-body>
                <fo:table-row>
                  <fo:table-cell><fo:block>FEES TOTAL</fo:block></fo:table-cell>
                  <fo:table-cell>
                    <fo:block>£<xsl:value-of select="sum(//fees)"/></fo:block>
                  </fo:table-cell>
                </fo:table-row>
                <fo:table-row>
                  <fo:table-cell><fo:block>MONTHLY INCOME</fo:block></fo:table-cell>
                  <fo:table-cell>
                    <fo:block>£<xsl:value-of select="format-number(sum(//standing-order), '#####.##')"/></fo:block>
                  </fo:table-cell>
                </fo:table-row>
              </fo:table-body>
            </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
  </xsl:template>

  <!-- ========================= -->
  <!-- FAMILY                    -->
  <!-- ========================= -->
  <xsl:template match="family">
    <xsl:element name="fo:table-row">
      <xsl:if test="standing-order='0'">
        <xsl:attribute name="color">green</xsl:attribute>
        <xsl:attribute name="font-weight">bold</xsl:attribute>
        <xsl:attribute name="text-decoration">line-through</xsl:attribute>
      </xsl:if>
      <fo:table-cell>
        <fo:block><xsl:value-of select="name"/></fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>£<xsl:value-of select="fees"/></fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block>
          <xsl:if test="standing-order!='0'">£<xsl:value-of select="standing-order"/></xsl:if>
        </fo:block>
      </fo:table-cell>
      <fo:table-cell>
        <fo:block font-size="9pt"><xsl:value-of select="fees-explanation"/></fo:block>
      </fo:table-cell>
    </xsl:element>
  </xsl:template>

</xsl:stylesheet>
