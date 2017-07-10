<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.1" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes"/>

  <!-- ========================= -->
  <!-- SQUADS                    -->
  <!-- ========================= -->
  <xsl:template match="/">

    <fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm" margin-top="2cm" margin-bottom="2cm" margin-left="2cm" margin-right="2cm">
          <fo:region-body/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <xsl:apply-templates />
    </fo:root>
  </xsl:template>

  <!-- ========================= -->
  <!-- SQUAD                     -->
  <!-- ========================= -->
  <xsl:template match="squad">
      <fo:page-sequence master-reference="simpleA4">
        <fo:flow flow-name="xsl-region-body">
          <fo:block font-size="16pt" font-weight="bold" space-after="5mm">Squad: <xsl:value-of select="name"/>
          </fo:block>
          <fo:block font-size="10pt">
            <fo:table table-layout="fixed" width="100%" border-collapse="separate" break-after="page">
              <fo:table-column column-width="3.5cm"/>
              <fo:table-column column-width="2.1cm"/>
              <fo:table-column column-width="2.6cm"/>
              <fo:table-column column-width="3.5cm"/>
              <fo:table-column column-width="5cm"/>
              <fo:table-header>
                <fo:table-row background-color="#ccc">
                  <fo:table-cell ><fo:block>NAME</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>DOB</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>PHONE</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>PARENT</fo:block></fo:table-cell>
                  <fo:table-cell><fo:block>EMAIL</fo:block></fo:table-cell>
                </fo:table-row>
              </fo:table-header>
              <fo:table-body>
                <xsl:apply-templates select="swimmers/swimmer"/>
              </fo:table-body>
            </fo:table>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
  </xsl:template>

  <!-- ========================= -->
  <!-- SWIMMER                   -->
  <!-- ========================= -->
  <xsl:template match="swimmer">
    <fo:table-row>
      <fo:table-cell><fo:block><xsl:value-of select="name"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="dob"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="phone"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="parent"/></fo:block></fo:table-cell>
      <fo:table-cell><fo:block><xsl:value-of select="email"/></fo:block></fo:table-cell>
    </fo:table-row>
  </xsl:template>
</xsl:stylesheet>
