package model.model;

import java.time.LocalDate;

    public class Trabajo {
        private int id;
        private String titulo;
        private String descripcion;
        private String tipo;
        private String experienciaRequerida;
        private String sueldo;

        public Trabajo(int id, String titulo, String descripcion, String tipo, String experienciaRequerida, String sueldo) {
            this.id = id;
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.tipo = tipo;
            this.experienciaRequerida = experienciaRequerida;
            this.sueldo = sueldo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public void setDescripcion(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getTipo() {
            return tipo;
        }

        public void setTipo(String tipo) {
            this.tipo = tipo;
        }

        public String getExperienciaRequerida() {
            return experienciaRequerida;
        }

        public void setExperienciaRequerida(String experienciaRequerida) {
            this.experienciaRequerida = experienciaRequerida;
        }

        public String getSueldo() {
            return sueldo;
        }

        public void setSueldo(String sueldo) {
            this.sueldo = sueldo;
        }
    }
