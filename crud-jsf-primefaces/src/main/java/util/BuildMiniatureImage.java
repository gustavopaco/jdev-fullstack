package util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class BuildMiniatureImage {

    public String createMiniatureImage(InputStream inputStream, String mimeType) throws IOException {

        /* Transforma em bufferImage chamando o metodo de ler Part file e retorna byte[]*/
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        /* Tipo da Imagem */
        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

        int fileheight = 200;
        int filewidth = 200;

        /* Criando miniatura */
        BufferedImage miniatureImage = new BufferedImage(filewidth, fileheight, type);
        Graphics2D graphics2D = miniatureImage.createGraphics();
        graphics2D.drawImage(bufferedImage, 0, 0, filewidth, fileheight, null);
        graphics2D.dispose();

        /* Escrevendo novamente a imagem em tamanho menor */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String extensao = mimeType.split("/")[1];
        ImageIO.write(miniatureImage, extensao, baos);

//        return "data:" + filePart.getContentType() + ";base64," + DatatypeConverter.printBase64Binary(baos.toByteArray()); **Qualquer um dos jeitos da certo**
        return "data:" + mimeType + ";base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
