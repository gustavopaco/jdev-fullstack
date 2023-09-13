package controller;

import dao.UsuarioDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import models.Endereco;
import models.Telefone;
import models.Usuario;
import util.Constantes;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@WebServlet("/cadastroCtl")
@MultipartConfig
public class CadastroServletController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO;
    private Usuario usuario;
    private Endereco endereco;
    private Telefone telefone;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch ((action != null) ? action : "redirecionar") {
            case "register" -> doRegister(req, resp);
            case "delete" -> doDelete(req, resp);
            case "edit" -> doEdit(req, resp);
            case "update" -> doPut(req, resp);
            case "home" -> doHome(req, resp);
            case "download" -> doDownload(req, resp);
            case "redirecionar" -> resp.sendRedirect("index.jsp");
        }
    }

    protected void doRegister(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String firstName = req.getParameter("first_name");
            String last_name = req.getParameter("last_name");
            String name = firstName.concat(" ").concat(last_name);
            String data = req.getParameter("birthday");
            LocalDate birthday = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            String gender = req.getParameter("gender");
            String cpf = req.getParameter("cpf");
            String zipcode = req.getParameter("zipcode");
            String phone = req.getParameter("phone");
            String phone_type = req.getParameter("phone_type");
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            String address = req.getParameter("address");
            String number = req.getParameter("number");
            String address2 = req.getParameter("address2");
            String state = req.getParameter("state");
            String neighborhood = req.getParameter("neighborhood");
            String city = req.getParameter("city");

            usuario = new Usuario();
            usuarioDAO = new UsuarioDAO();
            endereco = new Endereco();
            telefone = new Telefone();

            usuario.setName(name);
            usuario.setBirthday(birthday);
            switch (Integer.parseInt(gender)) {
                case 1 -> usuario.setGender(Constantes.MASCULINO);
                case 2 -> usuario.setGender(Constantes.FEMININO);
            }
            usuario.setCpf(cpf);
            usuario.setLogin(login);
            usuario.setPassword(password);

            endereco.setEnd_cep(zipcode);
            endereco.setEnd_rua(address);
            endereco.setEnd_numero(Integer.parseInt(number));
            endereco.setEnd_complemento(address2);
            endereco.setEnd_bairro(neighborhood);
            endereco.setEnd_cidade(city);
            endereco.setEnd_estado(state);

            telefone.setTel_numero(phone);
            telefone.setTel_tipo(phone_type);

            if (usuarioDAO.isLoginValid(login) && usuarioDAO.isCPFValid(cpf) && usuarioDAO.isAddressValid(endereco) && usuarioDAO.isTelefoneInsertValid(telefone)) {
                try {
                    usuarioDAO.cadastrarUsuario(usuario);
                    System.out.println("Usuario Cadastrado com Sucesso.");
                    usuario = usuarioDAO.pesquisarUsuarioByLogin(usuario.getLogin());

                    endereco.setId_usuario(usuario.getId());
                    usuarioDAO.insertEndereco(endereco);
                    System.out.println("Endereco cadastrado com Sucesso.");

                    telefone.setId_usuario(usuario.getId());
                    usuarioDAO.insertTelefone(telefone);
                    System.out.println("Telefone cadastrado com sucesso.");

                    req.getSession().setAttribute("usuarioSession", usuario);
                    req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
                    resp.sendRedirect("cadastroCtl?action=home");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
                    requestDispatcher.forward(req, resp);
                }
            } else {
                if (!usuarioDAO.isLoginValid(login)) {
                    req.setAttribute("msg1", "Login ja existe.*");
                }
                if (!usuarioDAO.isCPFValid(cpf)) {
                    req.setAttribute("msg2", "CPF ja existe.*");
                }
                if (!usuarioDAO.isAddressValid(endereco)) {
                    req.setAttribute("msg3", "Endereco ja Cadastrado*");
                }
                if (!usuarioDAO.isTelefoneInsertValid(telefone)) {
                    req.setAttribute("msg4", "Telefone ja cadastrado*");
                }
                req.setAttribute("user", usuario);
                req.setAttribute("telefone", telefone);
                req.setAttribute("endereco", endereco);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("cadastroActivity.jsp");
                requestDispatcher.forward(req, resp);
            }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        usuarioDAO = new UsuarioDAO();
        usuarioDAO.deletarUsuario(Long.parseLong(id));
        req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = Long.parseLong(req.getParameter("id"));
        usuarioDAO = new UsuarioDAO();
        usuario = new Usuario();
        usuario = usuarioDAO.pesquisarUsuario(id);
        req.getSession().setAttribute("usuarioEscolhido", usuario);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("detalhesActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //        try {
//            if (ServletFileUpload.isMultipartContent(req){
//                List<FileItem> fileItems = new ServletFileUpload(new DiskFileItemFactory()).parseRequest((RequestContext) req);
//
//                for (FileItem file : fileItems) {
//                    if(file.getFieldName().equals("foto")){
//                        String foto = Base64.getEncoder().encodeToString(file.get());
//                        System.out.println("A foto eh: " + foto);
//                    }
//                }
//            }
//        } catch (FileUploadException e) {
//            e.printStackTrace();
//        }

        usuario = new Usuario();
        Usuario usuarioEscolhido = (Usuario) req.getSession().getAttribute("usuarioEscolhido");
        usuarioDAO = new UsuarioDAO();

        Part imageFoto = req.getPart("foto");
        if (!imageFoto.getSubmittedFileName().isEmpty()) {
//        String foto = Base64.getEncoder().encodeToString(converteStreamParaByte(imageFoto.getInputStream())); CONVERTENDO INPUTSTREAM PARA STRING BASE64
            String fotoBase64 = Base64.getEncoder().encodeToString(imageFoto.getInputStream().readAllBytes());
            String tipoFoto = imageFoto.getContentType();
            String fileName = imageFoto.getSubmittedFileName();

            // Criando Miniatura da Imagem para Salvar no Banco.
            // Transformando em um BufferImage
            BufferedImage bufferedImage = ImageIO.read(imageFoto.getInputStream());

            // Pegando Tipo da Imagem
            int typeBufferedImage = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

            // Criando Imagem em Miniatura
            BufferedImage resizedImage = new BufferedImage(100,100,typeBufferedImage);
            Graphics2D graphics2D = resizedImage.createGraphics();
            graphics2D.drawImage(bufferedImage,0,0,100,100,null);
            graphics2D.dispose();

            // Escrevendo Imagem Novamente
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(resizedImage,"png",byteArrayOutputStream);

            // Formatando Imagem Base64 para Banco
            String miniaturaImagem = "data:image/png;Base64," + Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());

            // Finalizando Miniatura da Imagem para Salvar no Banco.

            usuario.setProfileImage(fotoBase64);
            usuario.setContentType(tipoFoto);
            usuario.setMiniaturaprofile(miniaturaImagem);
        } else {
            usuario.setUpdateImage(false);
//            usuario.setProfileImage(usuarioEscolhido.getProfileImage());
//            usuario.setContentType(usuarioEscolhido.getContentType());
//            usuario.setMiniaturaprofile(usuarioEscolhido.getMiniaturaprofile());
        }

        Part curriculo = req.getPart("curriculo");
        if (!curriculo.getSubmittedFileName().isEmpty()) {
            String curriculoBase64 = Base64.getEncoder().encodeToString(curriculo.getInputStream().readAllBytes());
            String curriculoContentType = curriculo.getContentType();
            String curriculoFileName = curriculo.getSubmittedFileName();
            usuario.setCurriculo(curriculoBase64);
            usuario.setCurriculoContentType(curriculoContentType);
        } else {
            usuario.setUpdatePDF(false);
//            usuario.setCurriculo(usuarioEscolhido.getCurriculo());
//            usuario.setCurriculoContentType(usuarioEscolhido.getCurriculoContentType());
        }


        usuario.setId(Long.parseLong(req.getParameter("id")));
        usuario.setLogin(req.getParameter("login"));
        if (req.getParameter("password").isEmpty() || req.getParameter("password") == null) {
            usuario.setPassword(req.getParameter("oldpassword"));
        } else {
            usuario.setPassword(req.getParameter("password"));
        }
        usuario.setName(req.getParameter("first_name").concat(" ").concat(req.getParameter("last_name")));
        LocalDate data = LocalDate.parse(req.getParameter("birthday"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        usuario.setBirthday(data);

        switch (Integer.parseInt(req.getParameter("gender"))) {
            case 1 -> usuario.setGender(Constantes.MASCULINO);
            case 2 -> usuario.setGender(Constantes.FEMININO);
        }
        usuario.setCpf(req.getParameter("cpf"));

        if (usuarioDAO.updateUsuario(usuario)) {
            System.out.println("Usuario alterado com sucesso.");
            req.setAttribute("usuarios", usuarioDAO.listarUsuarios());
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            System.out.println("Usuario econtrou algum problema ao ser alterado");
            doEdit(req, resp);
        }

    }

    protected void doHome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("usuarios", new UsuarioDAO().listarUsuarios());
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("homeActivity.jsp");
        requestDispatcher.forward(req, resp);
    }

    protected void doDownload(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id_usuario = req.getParameter("id_usuario");
        String ft = req.getParameter("ft");
        usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.pesquisarUsuario(Long.parseLong(id_usuario));
        String contentType = "";
        String file64 = "";

        if (ft.equals("img")) {
            file64 = usuario.getProfileImage();
            contentType = usuario.getContentType();
        } else if (ft.equals("curriculo")) {
            file64 = usuario.getCurriculo();
            contentType = usuario.getCurriculoContentType();
        }

        resp.setHeader("Content-Disposition", "attachment;filename=arquivo." + contentType.split("/")[1]);

        // CONVERTE A STRING BASE64 DA IMAGEM PARA BYTE[]
        byte[] FileBytes = Base64.getDecoder().decode(file64);

        // COLOCANDO OS BYTES EM UM OBJETO DE ENTRADA PARA PROCESSAMENTO
        InputStream ips = new ByteArrayInputStream(FileBytes);

        // INICIO DA RESPOSTA PARA O NAVEGADOR
        int read = 0;
        byte[] bytes = new byte[1024];
        OutputStream ops = resp.getOutputStream();

        while ((read = ips.read(bytes)) != -1) {
            ops.write(bytes, 0, read);
        }

        ops.flush();
        ops.close();
    }

    /* CONVERTE A ENTRADA DE FLUXO DE DADOS PARA ARRAY DE BYTES */
    private byte[] converteStreamParaByte(InputStream imagem) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int reads = imagem.read();
        while (reads != -1) {
            byteArrayOutputStream.write(reads);
            reads = imagem.read();
        }
        return byteArrayOutputStream.toByteArray();
    }
}
