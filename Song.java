import java.util.*;

public class Song {
    private String titulo;
    private double duracion;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDuracion() {
        return duracion;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public Song(String titulo, double duracion) {
        this.titulo = titulo;
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return "Cancion: " +
                "titulo='" + titulo + '\'' +
                ", duracion=" + duracion;
    }

    public static class Album {
        private String nombre;
        private String artista;

        private ArrayList<Song> canciones;

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getArtista() {
            return artista;
        }

        public void setArtista(String artista) {
            this.artista = artista;
        }

        public ArrayList<Song> getCanciones() {
            return canciones;
        }

        public void setCanciones(ArrayList<Song> canciones) {
            this.canciones = canciones;
        }

        public Album(String nombre, String artista) {
            this.nombre = nombre;
            this.artista = artista;
            this.canciones = new ArrayList<Song>();
        }

        private Song findSong(String titulo) {
            for (Song buscar : canciones) {
                if (buscar.getTitulo().equals(titulo)) {
                    return buscar;
                }
            }
            return null;
        }

        public boolean addSong(String titulo, double duracion) {
            if (findSong(titulo) == null) {
                this.canciones.add(new Song(titulo, duracion));
                System.out.println("Cancion nueva: " + titulo + ": " + duracion + ": " + "añadida.");
                return true;
            } else {
                System.out.println("La cancion: " + titulo + " ya existe");
                return false;
            }
        }

        public boolean addToPlaylist(int pista, LinkedList<Song> lista) {
            int indice = pista - 1;
            if ((indice >= 0) && (indice <= canciones.size())) {
                lista.add(canciones.get(indice));
                return true;
            } else {
                System.out.println("El album: " + getNombre() + " de " + getArtista() + " no tiene la pista: " + pista);
                return false;
            }
        }

        public boolean addToPlayList(String titulo, LinkedList<Song> lista) {
            Song buscar = findSong(titulo);
            if (buscar != null) {
                lista.add(buscar);
                System.out.println("La cancion: " + titulo + " esta en el album: " + getNombre() + " de " + getArtista());
                return true;
            }
            System.out.println("La cancion " + titulo + " no esta en el album: " + getNombre() + " de " + getArtista());
            return false;
        }

        static class Main {

            public static void main(String[] args) {
                System.out.println("...............Añadiendo canciones...............");
                ArrayList<Album> albums = new ArrayList<Album>(); //ArrayList de los albumes.
                //Creacion de albumes y añadir las canciones a los albumes.
                Album album = new Album("Use Your Illusion I", "Gun's N' Roses");
                album.addSong("Dust N' Bones", 5);
                album.addSong("Live And Let Die", 3);
                album.addSong("Live And Let Die", 3); //cancion repetida
                albums.add(album);
                album = new Album("Queen", "Queen");
                album.addSong("Keep Yourself Alive", 4);
                album.addSong("Doing All Right", 3);
                albums.add(album);

                //Bucle para sacar las canciones de los albumes
                for (int i = 0; i < albums.size(); i++) {
                    System.out.print("Canciones en los albumes: ");
                    System.out.println((albums.get(i).getCanciones()));
                }

                LinkedList<Song> lista = new LinkedList<Song>();
                //Añadimos Canciones Segundo album.
                albums.get(1).addToPlayList("Keep Yourself Alive", lista);
                albums.get(1).addToPlayList("Doing All Right", lista);
                albums.get(1).addToPlayList("Rock N' Roll", lista); //No existe la cancion

                //Añadimos Canciones Primer album
                albums.get(0).addToPlaylist(1, lista); //Dust N' Bones
                albums.get(0).addToPlaylist(2, lista); //Live and let die
                albums.get(0).addToPlaylist(5, lista); //no existe la pista
                System.out.println("...............Añadiendo canciones...............");
                play(lista); //Llamamos a la funcion play
            }

            public static void play(LinkedList<Song> lista2) {
                try {
                    Scanner scanner = new Scanner(System.in);
                    boolean continuar = true;

                    ListIterator<Song> it = lista2.listIterator();

                    if (lista2.isEmpty()) {
                        System.out.println("No hay canciones reproduciendose.");
                        return;
                    } else {
                        System.out.println('\n');
                        System.out.println("Reproduciendo la primera cancion: " + it.next().getTitulo());
                        System.out.println('\n');
                        Menu();
                    }

                    boolean haciaAdelante = true;
                    while (continuar) {
                        int opcion;
                        opcion = scanner.nextInt();
                        if (opcion < 0 || opcion > 6) {
                            System.out.println("Solo se admiten numeros del 0-6.");
                            System.out.println("Vuelve a intentarlo.");
                            Menu();
                        }
                        switch (opcion) {
                            case 0:
                                System.out.println("Adios vuelve cuando quieras.");
                                continuar = false;
                                break;

                            case 1:
                                try {
                                    if (!haciaAdelante) {
                                        if (it.hasNext())
                                            it.next();
                                        haciaAdelante = true;
                                    }
                                    if (it.hasNext()) {
                                        System.out.println("Reproduciendo siguiente cancion: " + it.next().getTitulo());
                                    } else {
                                        System.out.println("No hay más canciones en la playlist.");
                                        System.out.println("Reproduciendo: " + it.previous().getTitulo());
                                        haciaAdelante = false;
                                    }
                                    Menu();
                                } catch (NoSuchElementException a) {
                                    System.out.println("La lista esta vacia.");
                                    Menu();
                                }
                                break;

                            case 2:
                                if (haciaAdelante) {
                                    if (it.hasPrevious())
                                        it.previous();
                                    haciaAdelante = false;
                                }
                                if (it.hasPrevious()) {
                                    System.out.println("Reproduciendo anterior cancion: " + it.previous().getTitulo());
                                } else {
                                    if (lista2.isEmpty()) {
                                        System.out.println("No hay más canciones en la playlist.");
                                        System.out.println("La lista esta vacia.");
                                    } else {
                                        System.out.println("No hay mas canciones anteriores a: " + lista2.getFirst().getTitulo());
                                        System.out.println("Reproduciendo: " + it.next().getTitulo());
                                        haciaAdelante = true;
                                    }
                                }
                                Menu();
                                break;

                            case 3:
                                if (haciaAdelante) {
                                    if (it.hasPrevious()) {
                                        System.out.println("Se esta reproduciendo la cancion actual: " + it.previous().getTitulo());
                                        haciaAdelante = false;
                                    } else {
                                        System.out.println("Estas en la primera Cancion: " + lista2.getFirst().getTitulo());
                                    }
                                } else {
                                    if (it.hasNext()) {
                                        System.out.println("Se esta reproduciendo la cancion actual: " + it.next().getTitulo());
                                        haciaAdelante = true;
                                    } else {
                                        if (lista2.isEmpty()) {
                                            System.out.println("No hay más canciones en la playlist.");
                                            System.out.println("La lista esta vacia.");
                                        } else {
                                            System.out.println("Estas en la ultima cancion: " + lista2.getLast().getTitulo());
                                        }
                                    }
                                }
                                Menu();
                                break;

                            case 4:
                                if (lista2.isEmpty()) {
                                    System.out.println("La lista de reproduccion esta vacia.");
                                } else {
                                    System.out.println("Canciones de la lista de reproduccion.");
                                    for (int i = 0; i < lista2.size(); i++) {
                                        System.out.println(("Cancion " + (i + 1) + "." + " " + lista2.get(i).getTitulo()));
                                    }
                                }
                                Menu();
                                break;

                            case 5:
                                Menu();
                                break;

                            case 6:
                                try {
                                    it.remove();
                                    if (it.hasNext()) {
                                        System.out.println("La cancion actual ha sido eliminada.");
                                        System.out.println("Se esta reproduciendo la cancion siguiente: " + it.next().getTitulo());
                                        Menu();
                                    } else if (it.hasPrevious()) {
                                        System.out.println("Se esta reproduciendo la cancion anterior: " + it.previous().getTitulo());
                                        Menu();
                                    } else {
                                        System.out.println("Has eliminado todas las canciones.");
                                        Menu();
                                        break;
                                    }
                                } catch (IllegalStateException a) {
                                    System.out.println("Ya has eliminado todas las canciones.");
                                    Menu();
                                }
                        }
                    }
                } catch (InputMismatchException o) {
                    System.out.println("Has puesto un caracter o varios carateres.");
                    System.out.println('\n');
                    Main.main(null);

                }
            }
        }

        private static void Menu() {
            System.out.println("Elije una opcion del 0-6: ");
            System.out.println("0 - Salir de la lista de reproducción.");
            System.out.println("1 - Reproducir siguiente canción en la lista.");
            System.out.println("2 - Reproducir la canción previa de la lista.");
            System.out.println("3 - Repetir la canción actual.");
            System.out.println("4 - Imprimir la lista de canciones en la playlist.");
            System.out.println("5 - Volver a imprimir el menú.");
            System.out.println("6 - Eliminar cancion actual de la playlist.");
        }
    }
}
