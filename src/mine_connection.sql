-- phpMyAdmin SQL Dump
-- version 4.2.12deb2+deb8u1build0.15.04.1
-- http://www.phpmyadmin.net
--
-- Client :  localhost
-- Généré le :  Dim 19 Février 2017 à 23:04
-- Version du serveur :  5.6.28-0ubuntu0.15.04.1
-- Version de PHP :  5.6.4-4ubuntu6.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `mine_connection`
--

-- --------------------------------------------------------

--
-- Structure de la table `Association`
--

CREATE TABLE IF NOT EXISTS `Association` (
`id` int(10) unsigned NOT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `Etudiant`
--

CREATE TABLE IF NOT EXISTS `Etudiant` (
`id` int(11) unsigned NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `telephone` varchar(12) DEFAULT NULL,
  `promotion` int(2) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Etudiant`
--

INSERT INTO `Etudiant` (`id`, `nom`, `prenom`, `telephone`, `promotion`) VALUES
(71, 'Massiani', 'Pierre-François', '01', 16),
(72, 'Martinelli', 'Giacomo', '02', 16),
(73, 'Legrand', 'Domitille', '03', 16),
(74, 'Lennon', 'John', '04', 16),
(75, 'Massiani', 'Pierre-François', '01', 16),
(76, 'Martinelli', 'Giacomo', '02', 16),
(77, 'Legrand', 'Domitille', '03', 16),
(78, 'Li', 'Nicolas', '04', 16),
(83, 'Massiani', 'Pierre-François', '01', 16),
(84, 'Martinelli', 'Giacomo', '02', 16),
(85, 'Legrand', 'Domitille', '03', 16),
(86, 'Li', 'Nicolas', '04', 16);

-- --------------------------------------------------------

--
-- Structure de la table `Evenement`
--

CREATE TABLE IF NOT EXISTS `Evenement` (
`id` int(11) unsigned NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `description` text,
  `places` smallint(11) unsigned DEFAULT NULL,
  `places_restantes` smallint(5) unsigned DEFAULT NULL,
  `date` date DEFAULT NULL,
  `debut_h` tinyint(3) unsigned DEFAULT NULL,
  `debut_m` tinyint(3) unsigned DEFAULT NULL,
  `duree` int(11) DEFAULT NULL,
  `createur_est_etudiant` tinyint(1) DEFAULT NULL,
  `createur_etudiant_id` int(10) unsigned DEFAULT NULL,
  `createur_association_id` int(11) unsigned DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `Evenement`
--

INSERT INTO `Evenement` (`id`, `nom`, `description`, `places`, `places_restantes`, `date`, `debut_h`, `debut_m`, `duree`, `createur_est_etudiant`, `createur_etudiant_id`, `createur_association_id`) VALUES
(48, 'Événement test', 'Test', 10, 0, '2017-02-15', 10, 20, 30, 1, 71, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `evt_48`
--

CREATE TABLE IF NOT EXISTS `evt_48` (
`ordre_adhesion` int(10) unsigned NOT NULL,
  `id_etudiant` int(10) unsigned NOT NULL,
  `date_adhesion` date NOT NULL,
  `liste_principale` tinyint(1) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

--
-- Contenu de la table `evt_48`
--

INSERT INTO `evt_48` (`ordre_adhesion`, `id_etudiant`, `date_adhesion`, `liste_principale`) VALUES
(1, 83, '2017-02-15', 1);

--
-- Index pour les tables exportées
--

--
-- Index pour la table `Association`
--
ALTER TABLE `Association`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Etudiant`
--
ALTER TABLE `Etudiant`
 ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Evenement`
--
ALTER TABLE `Evenement`
 ADD PRIMARY KEY (`id`), ADD KEY `fk_createur_etudiant_id` (`createur_etudiant_id`), ADD KEY `fk_createur_association_id` (`createur_association_id`);

--
-- Index pour la table `evt_48`
--
ALTER TABLE `evt_48`
 ADD PRIMARY KEY (`ordre_adhesion`), ADD KEY `fk_id_etudiant_48` (`id_etudiant`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `Association`
--
ALTER TABLE `Association`
MODIFY `id` int(10) unsigned NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `Etudiant`
--
ALTER TABLE `Etudiant`
MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=87;
--
-- AUTO_INCREMENT pour la table `Evenement`
--
ALTER TABLE `Evenement`
MODIFY `id` int(11) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=49;
--
-- AUTO_INCREMENT pour la table `evt_48`
--
ALTER TABLE `evt_48`
MODIFY `ordre_adhesion` int(10) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=13;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `Evenement`
--
ALTER TABLE `Evenement`
ADD CONSTRAINT `fk_createur_association_id` FOREIGN KEY (`createur_association_id`) REFERENCES `Association` (`id`),
ADD CONSTRAINT `fk_createur_etudiant_id` FOREIGN KEY (`createur_etudiant_id`) REFERENCES `Etudiant` (`id`);

--
-- Contraintes pour la table `evt_48`
--
ALTER TABLE `evt_48`
ADD CONSTRAINT `fk_id_etudiant_48` FOREIGN KEY (`id_etudiant`) REFERENCES `Etudiant` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
