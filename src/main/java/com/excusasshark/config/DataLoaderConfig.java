package com.excusasshark.config;

import com.excusasshark.service.FragmentService;
import com.excusasshark.service.MemeService;
import com.excusasshark.service.LawService;
import com.excusasshark.model.Fragment;
import com.excusasshark.model.Meme;
import com.excusasshark.model.Law;
import com.excusasshark.model.FragmentType;
import com.excusasshark.model.LawType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Configuración para cargar datos iniciales
 * Inyecta datos de ejemplo al iniciar la aplicación
 */
@Configuration
public class DataLoaderConfig {
        private static final String ACTIVE_KEY = "active";
        private static final String TYPE_KEY = "type";
        private static final String TEXT_KEY = "text";
        private static final String CONTENT_KEY = "content";
        private static final String AUTHOR_KEY = "author";
        private static final String SOURCE_KEY = "source";
        private static final String CATEGORY_KEY = "category";

    @Bean
    public CommandLineRunner loadInitialData(
            FragmentService fragmentService,
            MemeService memeService,
            LawService lawService) {

                return args -> {
                        // Intentar cargar datos desde docs/json; si falla, usar fallback hardcodeado
                        boolean loadedFromJson = false;
                        try {
                                loadedFromJson = loadFromJson(fragmentService, memeService, lawService);
                        } catch (Exception e) {
                                // Continuar con fallback
                        }

                        if (loadedFromJson) {
                                return; // Datos cargados desde JSON exitosamente
                        }

                        // Fallback: carga hardcodeada
                        // Fragmentos - CONTEXTO
            fragmentService.create(Fragment.builder()
                    .type(FragmentType.CONTEXTO)
                    .text("El deploy de producción se realizó sin testing")
                    .source("Jira-2024")
                    .category("deployment")
                    .build());

            fragmentService.create(Fragment.builder()
                    .type(FragmentType.CONTEXTO)
                    .text("La base de datos estaba sin backups configurados")
                    .source("DevOps")
                    .category("infrastructure")
                    .build());

            // Fragmentos - CAUSA
            fragmentService.create(Fragment.builder()
                    .type(FragmentType.CAUSA)
                    .text("El código pasó entre las grietas del code review")
                    .source("Git")
                    .category("development")
                    .build());

            fragmentService.create(Fragment.builder()
                    .type(FragmentType.CAUSA)
                    .text("El algoritmo de caching fue optimizado sin considerar edge cases")
                    .source("Architecture")
                    .category("performance")
                    .build());

            // Fragmentos - CONSECUENCIA
            fragmentService.create(Fragment.builder()
                    .type(FragmentType.CONSECUENCIA)
                    .text("El sistema comenzó a procesar datos duplicados")
                    .source("Monitoring")
                    .category("data-integrity")
                    .build());

            fragmentService.create(Fragment.builder()
                    .type(FragmentType.CONSECUENCIA)
                    .text("Los usuarios reportaron latencia de 5 segundos en cada solicitud")
                    .source("Support")
                    .category("performance")
                    .build());

            // Fragmentos - RECOMENDACIÓN
            fragmentService.create(Fragment.builder()
                    .type(FragmentType.RECOMENDACION)
                    .text("Implementar testing automático en el pipeline CI/CD")
                    .source("QA")
                    .category("quality")
                    .build());

            fragmentService.create(Fragment.builder()
                    .type(FragmentType.RECOMENDACION)
                    .text("Hacer rollback y revisar con el equipo antes del próximo deploy")
                    .source("Lead")
                    .category("process")
                    .build());

            // Memes argentinos
            memeService.create(Meme.builder()
                    .text("Che, boludo... ¿viste que el servidor está down?")
                    .author("DevOps Guy")
                    .source("Slack")
                    .category("devops")
                    .build());

            memeService.create(Meme.builder()
                    .text("Con vos no se puede, hermano. Escribiste un SELECT * en producción")
                    .author("SQL Master")
                    .source("Team Room")
                    .category("database")
                    .build());

            memeService.create(Meme.builder()
                    .text("Mirá vos... commiteaste la contraseña de admin en el repo público")
                    .author("Security Officer")
                    .source("GitHub")
                    .category("security")
                    .build());

            // Leyes técnicas
            lawService.create(Law.builder()
                    .type(LawType.MURPHY)
                    .content("Si algo puede salir mal, saldrá mal en el peor momento posible")
                    .author("Murphy's Law")
                    .build());

            lawService.create(Law.builder()
                    .type(LawType.HOFSTADTER)
                    .content("Siempre es más lento de lo que esperas, aún si descontas esta ley")
                    .author("Hofstadter")
                    .build());

            lawService.create(Law.builder()
                    .type(LawType.DILBERT)
                    .content("Los engineers son comedores de pizza optimistas que ignoran los problemas")
                    .author("Dilbert")
                    .build());

            lawService.create(Law.builder()
                    .type(LawType.DEVOPS)
                    .content("Todo falla, así que planifica como si todo fallará mañana")
                    .author("DevOps Wisdom")
                    .build());

            lawService.create(Law.builder()
                    .type(LawType.DEVELOPER)
                    .content("El mejor código es el que no existe. El segundo mejor es el que ya testaron")
                    .author("Developer's Guide")
                    .build());
        };
    }

            private boolean loadFromJson(
                    FragmentService fragmentService,
                    MemeService memeService,
                    LawService lawService) throws IOException {
                // Ruta relativa al root del proyecto
                Path base = Path.of("docs", "json");
                Path fragmentsPath = base.resolve("fragments.json");
                Path memesPath = base.resolve("memes.json");
                Path lawsPath = base.resolve("laws.json");

                if (!Files.exists(fragmentsPath) && !Files.exists(memesPath) && !Files.exists(lawsPath)) {
                        return false; // no hay archivos
                }

                ObjectMapper mapper = new ObjectMapper();

                if (Files.exists(fragmentsPath)) {
                        loadFragmentsFromJson(fragmentService, mapper, fragmentsPath);
                }

                if (Files.exists(memesPath)) {
                        loadMemesFromJson(memeService, mapper, memesPath);
                }

                if (Files.exists(lawsPath)) {
                        loadLawsFromJson(lawService, mapper, lawsPath);
                }

                return true;
        }

        private void loadFragmentsFromJson(FragmentService fragmentService, ObjectMapper mapper, Path fragmentsPath) throws IOException {
                JsonNode root = mapper.readTree(Files.newBufferedReader(fragmentsPath));
                Iterator<JsonNode> it = root.elements();
                while (it.hasNext()) {
                        JsonNode node = it.next();
                        FragmentType type = null;
                        if (node.hasNonNull(TYPE_KEY)) {
                                try {
                                        type = FragmentType.valueOf(node.get(TYPE_KEY).asText().toUpperCase());
                                } catch (IllegalArgumentException ignored) {
                                        // Ignoramos tipos inválidos y dejamos el tipo en null intencionalmente
                                }
                        }
                        Fragment fragment = Fragment.builder()
                                        .type(type)
                                        .text(node.path(TEXT_KEY).asText(null))
                                        .source(node.path(SOURCE_KEY).asText(null))
                                        .category(node.path(CATEGORY_KEY).asText(null))
                                        .active(node.path(ACTIVE_KEY).asBoolean(true))
                                        .build();
                        fragmentService.create(fragment);
                }
        }

        private void loadMemesFromJson(MemeService memeService, ObjectMapper mapper, Path memesPath) throws IOException {
                JsonNode root = mapper.readTree(Files.newBufferedReader(memesPath));
                for (JsonNode node : root) {
                        Meme meme = Meme.builder()
                                        .text(node.path(TEXT_KEY).asText(null))
                                        .author(node.path(AUTHOR_KEY).asText(null))
                                        .source(node.path(SOURCE_KEY).asText(null))
                                        .category(node.path(CATEGORY_KEY).asText(null))
                                        .active(node.path(ACTIVE_KEY).asBoolean(true))
                                        .build();
                        memeService.create(meme);
                }
        }

        private void loadLawsFromJson(LawService lawService, ObjectMapper mapper, Path lawsPath) throws IOException {
                JsonNode root = mapper.readTree(Files.newBufferedReader(lawsPath));
                for (JsonNode node : root) {
                        LawType type = null;
                        if (node.hasNonNull(TYPE_KEY)) {
                                try {
                                        type = LawType.valueOf(node.get(TYPE_KEY).asText().toUpperCase());
                                } catch (IllegalArgumentException ignored) {
                                        // Ignoramos tipos inválidos y dejamos el tipo en null intencionalmente
                                }
                        }
                        Law law = Law.builder()
                                        .type(type)
                                        .content(node.path(CONTENT_KEY).asText(null))
                                        .author(node.path(AUTHOR_KEY).asText(null))
                                        .active(node.path(ACTIVE_KEY).asBoolean(true))
                                        .build();
                        lawService.create(law);
                }
        }
}
