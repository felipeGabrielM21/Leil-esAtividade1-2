/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */
import static com.mysql.cj.conf.PropertyKey.PASSWORD;
import static com.mysql.cj.conf.PropertyKey.USER;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    private static final String URL = "jdbc:mysql://localhost/Leiloes";
    private static final String USER = "root";
    private static final String PASSWORD = "fefe21@";

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;

    public void cadastrarProduto(ProdutosDTO produto) throws SQLException {

        String sql = "INSERT INTO cadastros(nome, valor, status) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pt = conn.prepareStatement(sql)) {

            pt.setString(1, produto.getNome());
            pt.setInt(2, produto.getValor());
            pt.setString(3, produto.getStatus());
            pt.executeUpdate();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar produto: " + ex.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() throws SQLException {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        String sql = "SELECT * FROM cadastros";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ProdutosDTO produtos = new ProdutosDTO();

                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));

                listagem.add(produtos);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
        }

        return listagem;
    }

    public void vender(Integer id) throws SQLException {

        String sql = "UPDATE cadastros SET status = 'Vendido' WHERE id = ? AND status = 'A Venda'";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ProdutosDTO produtos = new ProdutosDTO();

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID fornecido.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar produto: " + ex.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> ListaDeVendas() throws SQLException {
        ArrayList<ProdutosDTO> listagemVendidos = new ArrayList<>();

        String sql = "SELECT * FROM cadastros WHERE status = 'Vendido'";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {

             while (rs.next()) {
                ProdutosDTO produtos = new ProdutosDTO();

                produtos.setId(rs.getInt("id"));
                produtos.setNome(rs.getString("nome"));
                produtos.setValor(rs.getInt("valor"));
                produtos.setStatus(rs.getString("status"));

                listagemVendidos.add(produtos);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + ex.getMessage());
        }

        return listagemVendidos;
   

    }
}
