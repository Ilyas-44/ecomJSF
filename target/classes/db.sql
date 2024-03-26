
INSERT INTO `categorie` (`id`, `description`, `nom`) VALUES
(1, 'Boitier Tower, Big Tower, Destop or Slim', 'Ordinateur de Bureau'),
(2, 'Mémoire Vive', 'Mémoire RAM'),
(3, 'Accessoires de stockage', 'Clé USB'),
(4, 'Equipement Réseaux', 'Switch');

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id`, `designation`, `prix`, `quantite`, `sdr`, `idCateg`) VALUES
(1, 'HP Elote Book', 6700, 3, 0, 1),
(2, 'DDR4 32Go', 678, 5, 0, 2),
(3, 'USB Sandisk 16Go', 78, 12, 0, 3),
(4, 'Dell Latitude 3424', 3789, 2, 0, 1),
(5, 'DDR3 4Go', 120, 10, 0, 2),
(6, 'DDR3 2Go', 80, 8, 0, 2),
(7, 'Switch Cisco 32 ports', 2233, 3, 0, 4),
(8, 'Disque dur externe 2TB', 735, 5, 2, 3);

