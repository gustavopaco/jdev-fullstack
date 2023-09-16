package repository;

import com.google.gson.Gson;
import model.Cidades;
import model.Estados;
import model.Usuario;
import service.AddressResponse;
import util.DisplayMessages;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequestScoped
@Named
public class IDaoUsuarioImpl implements IDaoUsuario {

    @Inject
    private EntityManager entityManager;

    @Override
    public Usuario findAddress(String componentUI, Usuario usuario) {

        try {

            // Classe que faz conexao Restfull, SOAP ou qualquer outra pacote java.net
            URL url = new URL("https://viacep.com.br/ws/" + usuario.getCep() + "/json/");

            /* === METODO DE CONSULTA RESTFULL RECEBENDO UM InputStream, LENDO E COLOCANDO EM UM byte[], E USANDO O OBJETO STRING PARA LER O byte[] E REMOVER TODOS \n  === */
            byte[] bytes = url.openConnection().getInputStream().readAllBytes();
            String jSONCep = new String(bytes, StandardCharsets.UTF_8).replaceAll("\\n", "");

            AddressResponse gson = new Gson().fromJson(jSONCep, AddressResponse.class);

            if (gson.getErro() != null) {
                DisplayMessages.sendMessage(componentUI, FacesMessage.SEVERITY_ERROR, "Cant find Zipcode, try another one.");
            } else {
                usuario.setCep(gson.getCep());
                usuario.setLogradouro(gson.getLogradouro());
                usuario.setComplemento(gson.getComplemento());
                usuario.setBairro(gson.getBairro());
                usuario.setLocalidade(gson.getLocalidade());
                usuario.setUf(gson.getUf());
                usuario.setIbge(gson.getIbge());
                usuario.setGia(gson.getGia());
                usuario.setDdd(gson.getDdd());
                usuario.setSiafi(gson.getSiafi());
            }

            /* === METODO DE CONSULTA RESTFULL LENDO LINHA POR LINHA DO JSON (Alex) === */
//            URLConnection connection = url.openConnection();
//            InputStream inputStream = connection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
//
//            String cep = "";
//            StringBuilder jSONCep = new StringBuilder();
//
//            while ((cep = reader.readLine()) != null){
//
//                jSONCep.append(cep);
//            }
//            System.out.println(jSONCep);

        } catch (Exception e) {
            e.printStackTrace();
            DisplayMessages.sendMessage(componentUI, FacesMessage.SEVERITY_ERROR, "Zipcode invalid or Server offline.");
        }
        return usuario;
    }

    @Override
    public Usuario signIn(String email, String password) {

        return (Usuario) entityManager
                .createQuery("select u from Usuario u where u.email_usuario =:user_mail and u.password_usuario=:user_password")
                .setParameter("user_mail", email).setParameter("user_password", password).getSingleResult();
    }

    @Override
    public boolean emailAlreadyTaken(String email) {
        return (boolean) entityManager
                .createQuery("select case when count(u) > 0 then true else false end from Usuario u where u.email_usuario=:user_mail")
                .setParameter("user_mail", email).getSingleResult();
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<SelectItem> getStates() {
        List<SelectItem> selectItems = new ArrayList<>();
        List<Estados> estados = entityManager.createQuery("select e from Estados e order by e.nome").getResultList();

        for (Estados estado : estados) {
            selectItems.add(new SelectItem(estado, estado.getNome()));
        }

        return selectItems;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<SelectItem> getCities(Long estado_id) {
        List<SelectItem> selectItems = new ArrayList<>();
        List<Cidades> cidades = entityManager
                .createQuery("select c from Cidades c inner join Estados e on e.id = c.estados.id where c.estados.id =:estados_id order by c.nome")
                .setParameter("estados_id", estado_id).getResultList();

        for (Cidades cidade : cidades) {
            selectItems.add(new SelectItem(cidade, cidade.getNome()));
        }

        return selectItems;
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public List<Usuario> getUsersOrderByName() {

        return entityManager.createQuery("select u from Usuario u where u.email_usuario <> 'admin' order by u.nome_usuario").getResultList();
    }
}
