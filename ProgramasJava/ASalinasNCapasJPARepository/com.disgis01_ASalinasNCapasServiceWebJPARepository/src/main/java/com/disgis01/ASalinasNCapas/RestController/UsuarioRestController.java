/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.disgis01.ASalinasNCapas.RestController;

import com.disgis01.ASalinasNCapas.DAO.ColoniaJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.DireccionJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.EstadoJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.IDireccionJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IMunicipioJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IEstadoJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IColoniaJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IPaisJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IRollJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.IUsuarioJPADAORepository;
import com.disgis01.ASalinasNCapas.DAO.MunicipioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.PaisJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.ResultValidarDatos;
import com.disgis01.ASalinasNCapas.DAO.RollJPADAOImplementation;
import com.disgis01.ASalinasNCapas.DAO.UsuarioJPADAOImplementation;
import com.disgis01.ASalinasNCapas.JPA.Colonia;
import com.disgis01.ASalinasNCapas.JPA.Direccion;
import com.disgis01.ASalinasNCapas.JPA.Estado;
import com.disgis01.ASalinasNCapas.JPA.Municipio;
import com.disgis01.ASalinasNCapas.JPA.Pais;
import com.disgis01.ASalinasNCapas.JPA.Result;
import com.disgis01.ASalinasNCapas.JPA.Roll;
import com.disgis01.ASalinasNCapas.JPA.Usuario;
import com.disgis01.ASalinasNCapas.JPA.UsuarioDireccion;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.lang.Integer;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Alien 1
 */
@Tag(name = "Controlador Rest para Usuario", description = "Controllador donde se ejecuta todas las peticiones del cliente dentro de una Operacion CRUD")
@RestController
@RequestMapping("/usuarioapi")
public class UsuarioRestController {

    @Autowired
    private UsuarioJPADAOImplementation UsuarioJPADAOImplementation;
    @Autowired
    private DireccionJPADAOImplementation DireccionJPADAOImplementation;
    @Autowired
    private RollJPADAOImplementation RollJPADAOImplementation;
    @Autowired
    private PaisJPADAOImplementation PaisJPADAOImplementation;
    @Autowired
    private EstadoJPADAOImplementation EstadoJPADAOImplementation;
    @Autowired
    private MunicipioJPADAOImplementation MunicipioJPADAOImplementation;
    @Autowired
    private ColoniaJPADAOImplementation ColoniaJPADAOImplementation;
    @Autowired
    private IUsuarioJPADAORepository IUsuarioJPADAORepository;
    @Autowired
    private IDireccionJPADAORepository IDireccionJPADAORepository;
    @Autowired
    private IRollJPADAORepository IRollJPADAORepository;
    @Autowired
    private IPaisJPADAORepository IPaisJPADAORepository;
    @Autowired
    private IEstadoJPADAORepository IEstadoJPADAORepository;
    @Autowired
    private IMunicipioJPADAORepository IMunicipioJPADAORepository;
    @Autowired
    private IColoniaJPADAORepository IColoniaJPADAORepository;

    @Operation(summary = "Muestra todos los Usuarios junto con sus Direcciones, que no tienen baja logica de forma ascendente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra todos los UsuarioDireccion",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDireccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar todos los UsuarioDireccion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @GetMapping
    public ResponseEntity GetAll() {
        try {
            Result result = new Result();
            result.objects = new ArrayList<>();
            List<Usuario> usuarios = IUsuarioJPADAORepository.findByActivoUsuarioOrderByIdUsuarioAsc(1);
//            List<Usuario> usuarios = IUsuarioJPADAORepository.findAllByOrderByIdUsuarioAsc();
//            List<Usuario> usuarios = IUsuarioJPADAORepository.findAll();
            for (Usuario usuario : usuarios) {
                List<Direccion> direcciones = IDireccionJPADAORepository.findByUsuario_IdUsuario(usuario.getIdUsuario());

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = usuario;
                usuarioDireccion.direcciones = direcciones;
                result.objects.add(usuarioDireccion);

            }
            result.correct = true;

            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra correctamente un solo UsuarioDireccion",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDireccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar un solo UsuarioDireccion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra al Usuairo y su(s) Direccion(es), que no sean baja logica")
    @GetMapping("{idUsuario}")
    public ResponseEntity GetById(@PathVariable int idUsuario) {
        try {
//            Result result = UsuarioJPADAOImplementation.GetById(idUsuario);
            Result result = new Result();
            List<Usuario> usuarios = IUsuarioJPADAORepository.findAllByIdUsuario(idUsuario);
//            List<Usuario> usuarios = IUsuarioJPADAORepository.findAll();
            for (Usuario usuario : usuarios) {
                UsuarioDireccion usuariosDireccion = new UsuarioDireccion();
                usuariosDireccion.usuario = usuario;
//                List<Direccion> direcciones = IDireccionJPADAORepository.findByUsuarioId(usuario.getIdUsuario());

//                List<Direccion> direcciones = IDireccionJPADAORepository.findByUsuario_IdUsuario(usuario.getIdUsuario());
                List<Direccion> direcciones = IDireccionJPADAORepository.findByUsuario_IdUsuarioAndActivoDireccion(usuario.getIdUsuario(), 1);
                usuariosDireccion.direcciones = direcciones;
                result.object = usuariosDireccion;

            }
            result.correct = true;
            if (result.correct) {
               if (result.object== null) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra correctamente Usuario",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar Usuario"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra solo la informacion perteniecinete del Usuario")
    @GetMapping("soloUsuario/{idUsuario}")
    public ResponseEntity UsuarioGetSolo(@PathVariable int idUsuario) {
        try {
//            Result result = UsuarioJPADAOImplementation.UsuarioGetSolo(idUsuario);
            Result result = new Result();
            List<Usuario> usuarios = IUsuarioJPADAORepository.findAllByIdUsuario(idUsuario);
//            List<Usuario> usuarios = IUsuarioJPADAORepository.findAll();
            for (Usuario usuario : usuarios) {
                UsuarioDireccion usuariosDireccion = new UsuarioDireccion();
                usuariosDireccion.usuario = usuario;

                result.object = usuariosDireccion;

            }

            result.correct = true;
            if (result.correct) {
                if (result.object== null) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }
            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra correctamente Direccion",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Direccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar la Direccion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra solo la informacion perteneciente a la Direccion el cual esta relacionado a un Usuario de forma ascendente")
    @GetMapping("direccion/{idDireccion}")
    public ResponseEntity DireccionGetById(@PathVariable int idDireccion) {
        try {
//            Result result = DireccionJPADAOImplementation.DireccionGetById(idDireccion);
            Result result = new Result();
            List<Direccion> direcciones = IDireccionJPADAORepository.findAllByIdDireccion(idDireccion);
//            List<Usuario> usuarios = IUsuarioJPADAORepository.findAll();
            for (Direccion direccion : direcciones) {
                UsuarioDireccion usuariosDireccion = new UsuarioDireccion();
                usuariosDireccion.direccion = direccion;

                result.object = usuariosDireccion;

            }

            result.correct = true;
            if (result.correct) {
                if (result.object== null) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }
            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "UsuarioDireccion agregado correcetamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDireccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al nisertar UsuarioDireccion"),
        @ApiResponse(responseCode = "409", description = "Restriccion unica violada, cambie la informacion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Agrega la informacion del Usuario y la Direccion con la que se esta agregando")
    @PostMapping
    public ResponseEntity AddUsuarioDireccion(@RequestBody UsuarioDireccion usuarioDireccion) {
        try {
//            Result result = UsuarioJPADAOImplementation.Add(usuarioDireccion);
            Result result = new Result();
            try {

                Usuario usuarioJPA = usuarioDireccion.usuario;
                usuarioJPA.setActivoUsuario(1);

                Usuario usuario = IUsuarioJPADAORepository.save(usuarioJPA);

//            Optional<Roll> rollJPA = IRollJPADAORepository.findById(usuarioDireccion.usuario.roll.getIdRoll());
                Direccion direccionJPA = usuarioDireccion.direccion;
                direccionJPA.setActivoDireccion(1);
                direccionJPA.setUsuario(usuario);

                Direccion direccion = IDireccionJPADAORepository.save(usuarioDireccion.direccion);
//            Usuario usuario = usuarioRepository.save(usuarioDireccion.usuario);
//            usuarioDireccion.direccion.setUsuario(usuario);
//            Direccion direccion = direccionRepository.save(usuarioDireccion.direccion);

                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }
            int indice = result.errorMasassge.indexOf("unique");

            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else if (indice == -1) {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(409)).body("Restriccion unica violada, cambie la informacion");

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Direccion agregada correctamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Direccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al insertar la Direccion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Se Agrega una Direccon a un Usuario existente y cuya Id se utiliza para relacionar la Direccion nueva con el Usuario deseado")
    @PostMapping("direccion/{idUsuario}")
    public ResponseEntity AddDireccionByUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getUsuario() == null) {
                usuarioDireccion.setUsuario(new Usuario());
            }
            usuarioDireccion.getUsuario().setIdUsuario(idUsuario);
//            usuarioDireccion.getUsuario().setId(idUsuario);

//            Result result = DireccionJPADAOImplementation.Add(usuarioDireccion);
            Result result = new Result();
            try {

                Direccion direccionJPA = usuarioDireccion.getDireccion();
                direccionJPA.setActivoDireccion(1);

                direccionJPA.setUsuario(usuarioDireccion.getUsuario());
                Direccion direccion = IDireccionJPADAORepository.save(direccionJPA);

                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }
            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update correcto de Usuario",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "201", description = "Seguardo un nuevo Usuario",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "404", description = "Error al actualizar el Usuario"),
        @ApiResponse(responseCode = "409", description = "Restriccion unica violada, cambie la informacion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Actualiza la informacion del Usuario en base al Id que se este trabajando, que sea exitente y que no sea una baja logica, sin afectar la informacion de las Direcciones que esten relaciondas")
    @PutMapping("{idUsuario}")
    public ResponseEntity UpdateUsuario(@PathVariable int idUsuario, @RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getUsuario() == null) {
                usuarioDireccion.setUsuario(new Usuario());
            }
            usuarioDireccion.getUsuario().setIdUsuario(idUsuario);
//            usuarioDireccion.getUsuario().setId(idUsuario);
//            Result result = UsuarioJPADAOImplementation.Update(usuarioDireccion);
            Result result = new Result();
            try {

                Usuario usuarioJPA = usuarioDireccion.getUsuario();
                usuarioJPA.setActivoUsuario(1);

//                usuarioJPA.setUsuario(usuarioDireccion.getUsuario());
                Usuario usuario = IUsuarioJPADAORepository.save(usuarioJPA);

                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }
            int indice = result.errorMasassge.indexOf("unique");

            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else if (indice == -1) {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(409)).body("Restriccion unica violada, cambie la informacion");

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Update correcto de Direccion",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Direccion.class))}),
        @ApiResponse(responseCode = "201", description = "Se creo una nuava Direccion",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Direccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al actulizar la Direccion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Actualiza la informacion de la Direccion en base al Id del que se este trabajando y sin afecatar la informacion del Usuario que este relacionada")
    @PutMapping("direccion/{idDireccion}")
    public ResponseEntity UpdateDireccinoByUsuario(@PathVariable int idDireccion, @RequestBody UsuarioDireccion usuarioDireccion) {
        try {
            if (usuarioDireccion.getDireccion() == null) {
                usuarioDireccion.setDireccion(new Direccion());
            }
            usuarioDireccion.getDireccion().setIdDireccion(idDireccion);
//            Result result = DireccionJPADAOImplementation.Update(usuarioDireccion);
            Result result = new Result();
            try {

                Direccion direccionJPA = usuarioDireccion.getDireccion();
                direccionJPA.setActivoDireccion(1);

                direccionJPA.setUsuario(usuarioDireccion.getUsuario());
                Direccion direccion = IDireccionJPADAORepository.save(direccionJPA);

                result.correct = true;
            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }

            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Elimancion correcta de Usuario",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "404", description = "Error al eliminar el Usuario"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Hace una baja logica del Usuario en base al Id para no perder dicha informacion ademas que no se muetra en la vista general")
    @DeleteMapping("{idUsuario}")
    public ResponseEntity DeleteUsuarioDireccion(@PathVariable int idUsuario) {
        try {
//            Result result = UsuarioJPADAOImplementation.Delete(idUsuario);
            Result result = new Result();
            try {

                Optional<Usuario> usuarioJPA = IUsuarioJPADAORepository.findById(idUsuario);
                if (usuarioJPA != null) {
                    Usuario usuario = usuarioJPA.get();
                    usuario.setActivoUsuario(0);
                    Usuario usuarioEliminar = IUsuarioJPADAORepository.save(usuario);
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMasassge = "No se encontró la dirección con ID: " + idUsuario;
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }
            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Eliminacion correcta de Direccion",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Direccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al eliminar la Direccion"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Hace una baja logica de Direccion en base al Id para mantener la informacion ademas que no se muestra en la vista de detalles")
    @DeleteMapping("direccion/{idDireccion}")
    public ResponseEntity DeleteDireccionByUsuario(@PathVariable("idDireccion") int idDireccion) {
        try {
//            Result result = DireccionJPADAOImplementation.Delete(idDireccion);
            Result result = new Result();
            try {

                Optional<Direccion> direccionJPA = IDireccionJPADAORepository.findById(idDireccion);
                if (direccionJPA != null) {
                    Direccion direccion = direccionJPA.get();
                    direccion.setActivoDireccion(0);
                    Direccion direccionEliminar = IDireccionJPADAORepository.save(direccion);
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMasassge = "No se encontró la dirección con ID: " + idDireccion;
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }
            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muesta todos los Rolles",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Roll.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar Roll"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muetra todos los Rolles en orden asendente")
    @GetMapping("roll")
    public ResponseEntity GetAllRoll() {
        try {
//            Result result = RollJPADAOImplementation.GetAllRoll();
            Result result = new Result();
            result.objects = new ArrayList<>();
//            List<Roll> rolles = IRollJPADAORepository.findAllByOrderByIdRollAsc();
            List<Roll> rolles = IRollJPADAORepository.findAll();
            for (Roll roll : rolles) {

                result.objects.add(roll);

            }
            result.correct = true;

            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra todos los Paises",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pais.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar Pais"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra todos los Paises en forma ascendente")
    @GetMapping("pais")
    public ResponseEntity GetAllPais() {
        try {
//            Result result = PaisJPADAOImplementation.GetAllPais();
            Result result = new Result();
            result.objects = new ArrayList<>();
            List<Pais> paises = IPaisJPADAORepository.findAllByOrderByIdPaisAsc();
//            List<Pais> paises = IPaisJPADAORepository.findAll();
            for (Pais pais : paises) {

                result.objects.add(pais);

            }
            result.correct = true;
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra los Estados relacionados",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Estado.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar Estado"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra los Estados relacionados con el Id del Pais")
    @GetMapping("estado/{idPais}")
    public ResponseEntity EstadoGetById(@PathVariable int idPais) {
        try {
//            Result result = EstadoJPADAOImplementation.GetByIdEstados(idPais);
            Result result = new Result();
            result.objects = new ArrayList<>();
            List<Estado> estados = IEstadoJPADAORepository.findByPais_IdPais(idPais);
            for (Estado estado : estados) {

                result.objects.add(estado);

            }
            result.correct = true;
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra los Municipios relacionados",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Municipio.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar Municipio"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra los Municipios relacionados con el Id del Estado")
    @GetMapping("municipio/{idEstado}")
    public ResponseEntity MunicipioGetById(@PathVariable int idEstado) {
        try {
//            Result result = MunicipioJPADAOImplementation.GetByIdMunicipios(idEstado);
            Result result = new Result();
            result.objects = new ArrayList<>();
            List<Municipio> municipios = IMunicipioJPADAORepository.findByEstado_IdEstado(idEstado);
            for (Municipio municipio : municipios) {

                result.objects.add(municipio);

            }
            result.correct = true;
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Muestra las Colonias relacionadas",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Colonia.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar la Colonia"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Muestra las Colonias relacionados con el Id del Municipio")
    @GetMapping("colonia/{idMunicipio}")
    public ResponseEntity ColoniaGetById(@PathVariable int idMunicipio) {
        try {
//            Result result = ColoniaJPADAOImplementation.GetByIdColonias(idMunicipio);
            Result result = new Result();
            result.objects = new ArrayList<>();
            List<Colonia> colonias = IColoniaJPADAORepository.findByMunicipio_IdMunicipio(idMunicipio);
            for (Colonia colonia : colonias) {

                result.objects.add(colonia);

            }
            result.correct = true;
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Modificacion del Estatus del Usuario",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Usuario.class))}),
        @ApiResponse(responseCode = "404", description = "Error al modificar el estaus del Usuario"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Modifica el Estatus del Usuario que recibe por medio de la URL el cual es 0 para inactivo o 1 para activo")
    @GetMapping("estatus")
    public ResponseEntity EstatusUsuario(@RequestParam int IdUsuario, @RequestParam int ActivoUsuario) {
        try {
//            Result result = UsuarioJPADAOImplementation.UpdateActivo(IdUsuario, ActivoUsuario);
            Result result = new Result();
            try {

                Optional<Usuario> usuarioJPA = IUsuarioJPADAORepository.findById(IdUsuario);
                if (usuarioJPA != null) {
                    Usuario usuario = usuarioJPA.get();
                    usuario.setActivoUsuario(ActivoUsuario);
                    Usuario usuarioEliminar = IUsuarioJPADAORepository.save(usuario);
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMasassge = "No se encontró la dirección con ID: " + IdUsuario;
                }

            } catch (Exception ex) {
                result.correct = false;
                result.errorMasassge = ex.getLocalizedMessage();
                result.ex = ex;
            }
            if (result.correct) {
                return ResponseEntity.ok().body(result);

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Busqueda dinamica correcta",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDireccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Recuperar la informacion de la busqueda"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Recibe parametros de nombre, apellido paterno, apellido materno, el roll y el estatus del Usuario para hacer una Busqueda, se usa Like para ello")
    @PostMapping("busqueda")
    public ResponseEntity UsuarioBusqueda(@RequestBody UsuarioDireccion usuarioBusqueda) {
        try {
            Result result = UsuarioJPADAOImplementation.UsuarioBusqueda(usuarioBusqueda);
            if (result.correct) {
                if (result.objects.size() == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Sin información");
                } else {
                    return ResponseEntity.ok().body(result);
                }

            } else {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
            }

        } catch (Exception ex) {
//            return ResponseEntity.status(HttpStatus.valueOf(500)).body(ex.getLocalizedMessage());
            return ResponseEntity.status(HttpStatus.valueOf(500)).body("Internal Error Server");
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Documento validado correctamente",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDireccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error en el proceso de validacion del documento"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Recibe un Archivo TXT ó XSLX y se hace verifica el estado del archivo ademas de la informacion contenida, despues de ello envia un archivo log encriptado al usuairo que contienen informacion como la direccion de donde se guarda, true/false, la fecha de creacion y un comentario")
    @PostMapping("cargaMasiva")
    public ResponseEntity cargaMasiva(@RequestParam("archivo") MultipartFile archivo) throws IOException, NoSuchAlgorithmException, Exception {

        if (archivo != null && !archivo.isEmpty()) {
            String fileExtention = archivo.getOriginalFilename().split("\\.")[1];

            String root = System.getProperty("user.dir");
            String path = "src/main/resources/Archivos";
            String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String absolutePath = root + "/" + path + "/" + fecha + archivo.getOriginalFilename();

            List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

            if (fileExtention.equals("txt")) {
                usuariosDireccion = LecturaArchivoTXT(archivo);
                archivo.transferTo(new File(absolutePath));
            } else { //"xlsx"
                archivo.transferTo(new File(absolutePath));
                usuariosDireccion = LecturaArchivoXLSX(new File(absolutePath));
            }

            List<ResultValidarDatos> listaErrores = resultValidarDatos(usuariosDireccion);
            if (listaErrores.isEmpty()) {
                Result result = new Result();
                result.correct = true;
//                CrearArchivoLog(new File(absolutePath));
                SecretKey clave = generarClaveAES();
                String datosOriginales = CrearArchivoLog(new File(absolutePath));
                final String claveEncriptacion = "¡secreto!";
                String encriptado = encriptar(datosOriginales, claveEncriptacion);
                result.errorMasassge = encriptado;
                return ResponseEntity.ok().body(result);
            } else {
                return ResponseEntity.status(HttpStatus.valueOf(400)).body(listaErrores);
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body("Internal Error Server");
            }
        }
        return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Se insertanos todos los Usuarios y Direcciones",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioDireccion.class))}),
        @ApiResponse(responseCode = "404", description = "Error al Insertar los Usuarios y Direcciones"),
        @ApiResponse(responseCode = "409", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "400", description = "La operacion no se completo correctamente, intentelo mas tarde"),
        @ApiResponse(responseCode = "500", description = "Internal Error Server")})
    @Operation(summary = "Se hace la inserción de diferentes Usuarios y Direcciones contenido en el Archivo TXT ó XLSX, se actualiza la informacion del aricho log")
    @GetMapping("cargaMasiva/Procesar")
    public ResponseEntity AddUsuarioDireccionMasiva(@RequestParam("encriptado") String encriptado) throws IOException, NoSuchAlgorithmException, Exception {
        final String claveEncriptacion = "¡secreto!";
        String desencriptado = desencriptar(encriptado, claveEncriptacion);
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\digis\\OneDrive\\Documentos\\Adalberto Salinas Jose\\ProgramasJava\\ASalinasNCapasJSP\\com.disgis01_ASalinasNCapasServiceWeb\\" + desencriptado))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos[1].equals("true") && datos[3].equals("el documento esta listo para usar una vez")) {
                    String archivo = datos[0];
                    if (archivo != null && !archivo.isEmpty()) {
                        String fileExtention = archivo.split("\\.")[1];

                        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

                        if (fileExtention.equals("txt")) {
                            MultipartFile multipartFile = convertFileToMultipartFile(new File(archivo));
                            usuariosDireccion = LecturaArchivoTXT(multipartFile);
                        } else { //"xlsx"
                            usuariosDireccion = LecturaArchivoXLSX(new File(archivo));
                        }

                        List<ResultValidarDatos> listaErrores = resultValidarDatos(usuariosDireccion);
                        if (listaErrores.isEmpty()) {
                            Result result = UsuarioJPADAOImplementation.Add(usuariosDireccion);
                            int indice = result.errorMasassge.indexOf("unique");
                            if (result.correct) {
                                String comentario = "el documento subido correctamente";
                                FileWriter writer = new FileWriter(archivo, true);
                                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                                bufferedWriter.write(datos[0] + "|false|" + datos[2] + "|" + comentario);
                                bufferedWriter.close();
                                writer.close();
                                return ResponseEntity.ok().body(result);

                            } else if (indice == -1) {
//                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                                return ResponseEntity.status(HttpStatus.valueOf(409)).body("Restriccion unica violada, cambie la informacion");

                            } else {
//                                return ResponseEntity.status(HttpStatus.valueOf(400)).body(result);
                                return ResponseEntity.status(HttpStatus.valueOf(400)).body("La operacion no se completo correctamente, intentelo mas tarde");
                            }
                        } else {
                            return ResponseEntity.status(HttpStatus.valueOf(400)).body(listaErrores);
//                            return ResponseEntity.status(HttpStatus.valueOf(400)).body("Internal Error Server");
                        }
                    }
                } else {
                    String archivo = datos[0];
                    String comentario = "contienen un error el documento";
                    FileWriter writer = new FileWriter(archivo, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(datos[0] + "|false|" + datos[2] + "|" + comentario);
                    bufferedWriter.close();
                    writer.close();

                    return (ResponseEntity) ResponseEntity.status(HttpStatus.valueOf(400));
                }
            }//termina el while
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("El archivo está vacío", HttpStatus.BAD_REQUEST);
    }

    public List<UsuarioDireccion> LecturaArchivoTXT(MultipartFile archivo) {
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (InputStream inputStream = archivo.getInputStream(); BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {
            bufferedReader.readLine();
            String linea = "";
            while ((linea = bufferedReader.readLine()) != null) {
                String[] datos = linea.split("\\|");

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setNombreUsuario(datos[0]);
                usuarioDireccion.usuario.setApellidoPatUsuario(datos[1]);
                usuarioDireccion.usuario.setApellidoMatUsuario(datos[2]);
                usuarioDireccion.usuario.setFechaNacimeintoUsuario(new SimpleDateFormat("yyyy-MM-dd").parse(datos[3]));
                usuarioDireccion.usuario.setSexoUsuario(datos[4]);
                usuarioDireccion.usuario.setCorreoUsuario(datos[5]);
                usuarioDireccion.usuario.setCelularUsuario(datos[6]);
                usuarioDireccion.usuario.setPasswordUsuario(datos[7]);
                usuarioDireccion.usuario.setTelefonoUsuario(datos[8]);
                usuarioDireccion.usuario.setUserNombreUsuario(datos[9]);

                usuarioDireccion.usuario.roll = new Roll();
                usuarioDireccion.usuario.roll.setIdRoll(Integer.parseInt(datos[10]));

                usuarioDireccion.direccion = new Direccion();
                usuarioDireccion.direccion.setCalle(datos[11]);
                usuarioDireccion.direccion.setNumeroInterior(datos[12]);
                usuarioDireccion.direccion.setNumeroExterior(datos[13]);

                usuarioDireccion.direccion.colonia = new Colonia();
                usuarioDireccion.direccion.colonia.setIdColonia(Integer.parseInt(datos[14]));

                usuariosDireccion.add(usuarioDireccion);
            }
        } catch (Exception e) {
            usuariosDireccion = null;
        }
        return usuariosDireccion;
    }

    public List<UsuarioDireccion> LecturaArchivoXLSX(File archivo) {
        List<UsuarioDireccion> usuariosDireccion = new ArrayList<>();

        try (XSSFWorkbook workbook = new XSSFWorkbook(archivo);) {
            XSSFSheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                usuarioDireccion.usuario = new Usuario();

                usuarioDireccion.usuario.setNombreUsuario(row.getCell(0) != null ? row.getCell(0).toString() : "");
                usuarioDireccion.usuario.setApellidoPatUsuario(row.getCell(1) != null ? row.getCell(1).toString() : "");
                usuarioDireccion.usuario.setApellidoMatUsuario(row.getCell(2) != null ? row.getCell(2).toString() : "");
                usuarioDireccion.usuario.setFechaNacimeintoUsuario(row.getCell(3).getDateCellValue());
                usuarioDireccion.usuario.setSexoUsuario(row.getCell(4) != null ? row.getCell(4).toString() : "");
                usuarioDireccion.usuario.setCorreoUsuario(row.getCell(5) != null ? row.getCell(5).toString() : "");
                usuarioDireccion.usuario.setCelularUsuario(row.getCell(6) != null ? row.getCell(6).toString() : "");
                usuarioDireccion.usuario.setPasswordUsuario(row.getCell(7) != null ? row.getCell(7).toString() : "");
                usuarioDireccion.usuario.setTelefonoUsuario(row.getCell(8) != null ? row.getCell(8).toString() : "");
                usuarioDireccion.usuario.setCURPUsuario(row.getCell(9) != null ? row.getCell(9).toString() : "");
                usuarioDireccion.usuario.setUserNombreUsuario(row.getCell(10) != null ? row.getCell(10).toString() : "");

                usuarioDireccion.usuario.roll = new Roll();
                usuarioDireccion.usuario.roll.setIdRoll((int) row.getCell(11).getNumericCellValue());

                usuarioDireccion.direccion = new Direccion();
                usuarioDireccion.direccion.setCalle(row.getCell(12) != null ? row.getCell(12).toString() : "");
                usuarioDireccion.direccion.setNumeroInterior(row.getCell(13) != null ? row.getCell(13).toString() : "");
                usuarioDireccion.direccion.setNumeroExterior(row.getCell(14) != null ? row.getCell(14).toString() : "");

                usuarioDireccion.direccion.colonia = new Colonia();
                usuarioDireccion.direccion.colonia.setIdColonia((int) row.getCell(15).getNumericCellValue());

                usuariosDireccion.add(usuarioDireccion);
            }

        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return usuariosDireccion;
    }

    private List<ResultValidarDatos> resultValidarDatos(List<UsuarioDireccion> usuarios) {
        List<ResultValidarDatos> listaErrores = new ArrayList<>();
        int fila = 1;
        if (usuarios == null) {
            listaErrores.add(new ResultValidarDatos(0, "La lista no existe", "Lista inexistente"));
        } else if (usuarios.isEmpty()) {
            listaErrores.add(new ResultValidarDatos(0, "La lista etsa vacia", "Lista vacia"));
        } else {
            for (UsuarioDireccion usuarioDireccion : usuarios) {
                if (usuarioDireccion.usuario.getNombreUsuario() == null || usuarioDireccion.usuario.getNombreUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getNombreUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getApellidoPatUsuario() == null || usuarioDireccion.usuario.getApellidoPatUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getApellidoPatUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getApellidoMatUsuario() == null || usuarioDireccion.usuario.getApellidoMatUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getApellidoMatUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getFechaNacimeintoUsuario() == null || new SimpleDateFormat("yyyy-MM-dd").format(usuarioDireccion.usuario.getFechaNacimeintoUsuario()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, new SimpleDateFormat("yyyy-MM-dd").format(usuarioDireccion.usuario.getFechaNacimeintoUsuario()), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getSexoUsuario() == null || usuarioDireccion.usuario.getSexoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getSexoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getCorreoUsuario() == null || usuarioDireccion.usuario.getCorreoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getCorreoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getCelularUsuario() == null || usuarioDireccion.usuario.getCelularUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getCelularUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getPasswordUsuario() == null || usuarioDireccion.usuario.getPasswordUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getPasswordUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getTelefonoUsuario() == null || usuarioDireccion.usuario.getTelefonoUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getTelefonoUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.getUserNombreUsuario() == null || usuarioDireccion.usuario.getUserNombreUsuario().equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, usuarioDireccion.usuario.getUserNombreUsuario(), "Campo Obligatorio"));
                }
                if (usuarioDireccion.usuario.roll.getIdRoll() == -1 || Integer.toString(usuarioDireccion.usuario.roll.getIdRoll()).equals("")) {
                    listaErrores.add(new ResultValidarDatos(fila, Integer.toString(usuarioDireccion.usuario.roll.getIdRoll()), "Campo Obligatorio"));
                }
                fila++;
            }
        }
        return listaErrores;
    }

    public static MultipartFile convertFileToMultipartFile(File file, String paramName) throws IOException {
        String contentType = Files.probeContentType(file.toPath());
        try (FileInputStream input = new FileInputStream(file)) {
            return new MockMultipartFile(
                    paramName,
                    file.getName(),
                    contentType,
                    input
            );
        }
    }

    public static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        return convertFileToMultipartFile(file, "file"); // default param name
    }

    public static String CrearArchivoLog(File absolutePath) {
        String nombrefecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String nombreArchivo = "ruta_archivo" + nombrefecha;
        String rutaArchivoTxt = "src/main/resources/Archivos/" + nombreArchivo + ".txt";
        File rutaArchivoAEscribir = absolutePath;
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String comentario = "el documento esta listo para usar una vez";
        try {
            File archivo = new File(rutaArchivoTxt);
            if (archivo.createNewFile()) {
                System.out.println("Archivo creado: " + archivo.getName());
                FileWriter writer = new FileWriter(archivo);
                writer.write(rutaArchivoAEscribir.toString() + "|true|" + fecha + "|" + comentario);
                writer.close();
                System.out.println("Ruta guardada en el archivo.");
            } else {
                System.out.println("El archivo ya existe.");
            }

        } catch (IOException e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        }
        return rutaArchivoTxt;
    }

    public static SecretKey generarClaveAES() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256); // 128, 192 o 256 bits
        return keyGenerator.generateKey();
    }

    private SecretKeySpec crearClave(String clave) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        byte[] claveEncriptacion = clave.getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        claveEncriptacion = sha.digest(claveEncriptacion);
        claveEncriptacion = Arrays.copyOf(claveEncriptacion, 16);
        SecretKeySpec secretKey = new SecretKeySpec(claveEncriptacion, "AES");

        return secretKey;
    }

    public String encriptar(String datos, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] datosEncriptar = datos.getBytes("UTF-8");
        byte[] bytesEncriptados = cipher.doFinal(datosEncriptar);
        String encriptado = Base64.getEncoder().encodeToString(bytesEncriptados);

        return encriptado;
    }

    public String desencriptar(String datosEncriptados, String claveSecreta) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec secretKey = this.crearClave(claveSecreta);

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] bytesEncriptados = Base64.getDecoder().decode(datosEncriptados);
        byte[] datosDesencriptados = cipher.doFinal(bytesEncriptados);
        String datos = new String(datosDesencriptados);

        return datos;
    }
}
