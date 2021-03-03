FROM java:8
WORKDIR /
ADD pdfgen.jar pdfgen.jar
RUN mkdir /root/facturas
RUN mkdir /root/facturas/output
COPY template.xsl /root/facturas
CMD ["java", "-jar", "pdfgen.jar"]