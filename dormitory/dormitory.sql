-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: 2019-03-26 13:55:49
-- 服务器版本： 5.7.17-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dormitory`
--

-- --------------------------------------------------------

--
-- 表的结构 `announcement`
--

CREATE TABLE `announcement` (
  `ID` int(11) NOT NULL,
  `Atime` datetime NOT NULL,
  `houseparentID` varchar(12) NOT NULL,
  `content` text NOT NULL,
  `title` varchar(25) NOT NULL,
  `govern` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `announcement`
--

INSERT INTO `announcement` (`ID`, `Atime`, `houseparentID`, `content`, `title`, `govern`) VALUES
(1, '2019-02-17 22:19:56', '12', '看看效果咋样哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈看看效果咋样哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈看看效果咋样哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈看看效果咋样哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈', '第一条公告', 'A'),
(2, '2019-03-08 21:55:52', '12', '没什么事情', '第二次发公告', 'A'),
(3, '2019-03-08 21:56:19', '1', '来试试', '第三个公告', 'A');

-- --------------------------------------------------------

--
-- 表的结构 `departinfo`
--

CREATE TABLE `departinfo` (
  `departID` int(10) UNSIGNED NOT NULL,
  `registerDate` datetime NOT NULL,
  `ID` varchar(12) NOT NULL,
  `dormID` varchar(10) NOT NULL,
  `departCause` text NOT NULL,
  `departTime` datetime NOT NULL,
  `backTime` datetime NOT NULL,
  `contact` varchar(12) NOT NULL,
  `belong` varchar(12) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `departinfo`
--

INSERT INTO `departinfo` (`departID`, `registerDate`, `ID`, `dormID`, `departCause`, `departTime`, `backTime`, `contact`, `belong`, `name`) VALUES
(1, '2019-02-18 16:03:31', '10001', 'A100', '去医院看病', '2019-02-18 15:57:00', '2019-02-18 18:00:00', '123456789011', 'A', 'admin'),
(2, '2019-02-19 12:43:06', '100', '', '生病', '2019-02-19 12:42:00', '2019-02-19 11:42:00', '1234863493', 'A', '100同学');

-- --------------------------------------------------------

--
-- 表的结构 `postsinfo`
--

CREATE TABLE `postsinfo` (
  `PostsID` int(10) UNSIGNED NOT NULL,
  `LatestReplyTime` datetime NOT NULL,
  `PostsDate` datetime NOT NULL,
  `ID` varchar(12) NOT NULL,
  `name` varchar(10) NOT NULL,
  `postsTitle` text NOT NULL,
  `postsContent` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `postsinfo`
--

INSERT INTO `postsinfo` (`PostsID`, `LatestReplyTime`, `PostsDate`, `ID`, `name`, `postsTitle`, `postsContent`) VALUES
(1, '2019-03-20 10:50:48', '2019-03-18 18:09:55', '10001', 'admin', '第一次测试', '哈哈'),
(2, '2019-03-18 18:33:41', '2019-03-18 18:33:41', '10001', 'admin', '第二次', '哈哈哈'),
(3, '2019-03-18 18:35:21', '2019-03-18 18:35:21', '10001', 'admin', '第三次', '额'),
(9, '2019-03-19 22:45:11', '2019-03-18 18:36:18', '10001', 'admin', '九', ''),
(10, '2019-03-18 20:21:54', '2019-03-18 20:21:21', '10002', 'abc', '十', ''),
(13, '2019-03-20 10:40:38', '2019-03-19 12:58:00', '10001', 'admin', '龙门', ''),
(14, '2019-03-20 19:42:06', '2019-03-20 19:35:07', '10001', 'admin', '何', ''),
(15, '2019-03-20 19:35:50', '2019-03-20 19:35:50', '10001', 'admin', '快乐', '');

-- --------------------------------------------------------

--
-- 表的结构 `repairinfo`
--

CREATE TABLE `repairinfo` (
  `ApplyID` int(10) UNSIGNED NOT NULL,
  `ApplyDate` datetime NOT NULL,
  `dormID` varchar(10) NOT NULL,
  `RepairName` text,
  `DamageCause` text,
  `Details` text,
  `Contact` varchar(11) NOT NULL,
  `OtherRemarks` text,
  `belong` varchar(12) NOT NULL,
  `Status` smallint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `repairinfo`
--

INSERT INTO `repairinfo` (`ApplyID`, `ApplyDate`, `dormID`, `RepairName`, `DamageCause`, `Details`, `Contact`, `OtherRemarks`, `belong`, `Status`) VALUES
(1, '2019-02-14 18:07:48', 'A100', '日光灯', '灯管坏了', '靠近阳台那边的', '12345678901', '无', 'A', 0),
(2, '2019-02-14 19:55:31', 'C000', '门', '被踢烂了', '一不小心踢烂了', '1234567890', '没了', 'A', 0),
(4, '2019-02-15 18:22:30', 'C000', '把手', '严重损坏', '无', '1472583699', '', 'C', 0);

-- --------------------------------------------------------

--
-- 表的结构 `sendmessageinfo`
--

CREATE TABLE `sendmessageinfo` (
  `SendTime` datetime NOT NULL,
  `SenderID` varchar(12) NOT NULL,
  `SenderName` varchar(10) NOT NULL,
  `PostsID` int(11) NOT NULL,
  `Message` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `sendmessageinfo`
--

INSERT INTO `sendmessageinfo` (`SendTime`, `SenderID`, `SenderName`, `PostsID`, `Message`) VALUES
('2019-03-18 20:21:54', '10002', 'abc', 10, '和'),
('2019-03-19 22:45:11', '10001', 'admin', 9, '快乐'),
('2019-03-20 10:40:32', '10001', 'admin', 13, '歌'),
('2019-03-20 10:40:38', '10001', 'admin', 13, '哦哦哦'),
('2019-03-20 19:42:06', '10001', 'admin', 14, '龙猫');

-- --------------------------------------------------------

--
-- 表的结构 `signed`
--

CREATE TABLE `signed` (
  `SID` varchar(12) NOT NULL,
  `recordID` int(11) NOT NULL,
  `signedtime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `signed`
--

INSERT INTO `signed` (`SID`, `recordID`, `signedtime`) VALUES
('1', 3, '2019-03-09 18:36:15'),
('1', 5, '2019-03-09 19:45:16'),
('1', 6, '2019-03-09 19:53:51'),
('1', 7, '2019-03-09 20:03:22'),
('10', 7, '2019-03-09 20:04:58'),
('100', 3, '2019-03-09 19:28:42'),
('100', 7, '2019-03-09 20:04:26');

-- --------------------------------------------------------

--
-- 表的结构 `signrecord`
--

CREATE TABLE `signrecord` (
  `ID` int(11) NOT NULL,
  `Rtime` datetime NOT NULL,
  `houseparentID` varchar(12) NOT NULL,
  `nums` smallint(6) NOT NULL DEFAULT '0',
  `title` varchar(30) NOT NULL,
  `govern` varchar(12) NOT NULL,
  `totalnums` smallint(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `signrecord`
--

INSERT INTO `signrecord` (`ID`, `Rtime`, `houseparentID`, `nums`, `title`, `govern`, `totalnums`) VALUES
(1, '0000-00-00 00:00:00', '12', 0, '我的第一次签到', 'A', 0),
(2, '0000-00-00 00:00:00', '12', 0, '我的第一次签到', 'A', 0),
(3, '2019-03-09 15:53:05', '12', 2, '第三次签到', 'A', 2),
(5, '2019-03-09 19:44:52', '12', 0, '第四次签到', 'A', 3),
(6, '2019-03-09 19:53:22', '1', 0, '第五次签到', 'A', 3),
(7, '2019-03-09 20:02:56', '1', 3, '第七次签到一定要成功啊', 'A', 3);

-- --------------------------------------------------------

--
-- 表的结构 `stayinfo`
--

CREATE TABLE `stayinfo` (
  `stayID` int(10) UNSIGNED NOT NULL,
  `registerDate` datetime NOT NULL,
  `ID` varchar(12) NOT NULL,
  `dormID` varchar(10) NOT NULL,
  `startDate` datetime NOT NULL,
  `endDate` datetime NOT NULL,
  `contact` varchar(12) NOT NULL,
  `belong` varchar(12) NOT NULL,
  `name` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `stayinfo`
--

INSERT INTO `stayinfo` (`stayID`, `registerDate`, `ID`, `dormID`, `startDate`, `endDate`, `contact`, `belong`, `name`) VALUES
(1, '2019-02-19 12:43:35', '100', '', '2019-02-19 12:43:00', '2019-02-19 13:00:00', '153758884665', 'A', '100同学');

-- --------------------------------------------------------

--
-- 表的结构 `userinfo`
--

CREATE TABLE `userinfo` (
  `ID` varchar(12) NOT NULL,
  `name` varchar(10) NOT NULL,
  `dormID` varchar(10) NOT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `password` varchar(11) NOT NULL,
  `nickname` varchar(10) DEFAULT NULL,
  `belong` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `userinfo`
--

INSERT INTO `userinfo` (`ID`, `name`, `dormID`, `phone`, `password`, `nickname`, `belong`) VALUES
('1', '1同学', 'A101', '121321', '123456', 'HA', 'A'),
('10', '10同学', 'A000', '3131351', '123456', 'HAA', 'A'),
('100', '100同学', 'A101', '5646451', '123456', 'AH', 'A'),
('10001', 'admin', 'A100', '12345678', '123456', 'AHH', 'A'),
('10002', 'abc', 'A000', '1234567890', '123456', 'FS', 'A'),
('10003', 'abc', 'A000', '1234567890', '123456', 'DFS', 'A'),
('20001', 'A栋学生', 'A133', '1231456655', '123456', 'mike', 'A'),
('20002', 'B栋学生', 'B333', '123456789', '123456', 'didi', 'B'),
('20003', 'C栋学生', 'C414', '134654645', '123456', 'Amy', 'C'),
('321', 'qqq', 'B000', '1236547890', '123456', 'dada', 'B'),
('666', '正式', 'C000', '1472583690', '123456', 'OK', 'C');

-- --------------------------------------------------------

--
-- 表的结构 `userinfoh`
--

CREATE TABLE `userinfoh` (
  `ID` varchar(12) NOT NULL,
  `name` varchar(10) NOT NULL,
  `govern` varchar(12) NOT NULL,
  `phone` varchar(12) NOT NULL,
  `password` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转存表中的数据 `userinfoh`
--

INSERT INTO `userinfoh` (`ID`, `name`, `govern`, `phone`, `password`) VALUES
('1', 'Fast', 'A', '111111111', '123456'),
('10000', 'Admin', 'A', '123456', '123456'),
('12', 'DDD', 'A', '12312', '123456'),
('20001', 'A栋宿管', 'A', '132132465', '123456'),
('20002', 'B栋宿管', 'B', '1346516165', '123456'),
('20003', 'C栋宿管', 'C', '1654649894', '123456');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `announcement`
--
ALTER TABLE `announcement`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `houseparentID` (`houseparentID`);

--
-- Indexes for table `departinfo`
--
ALTER TABLE `departinfo`
  ADD PRIMARY KEY (`departID`);

--
-- Indexes for table `postsinfo`
--
ALTER TABLE `postsinfo`
  ADD PRIMARY KEY (`PostsID`);

--
-- Indexes for table `repairinfo`
--
ALTER TABLE `repairinfo`
  ADD PRIMARY KEY (`ApplyID`);

--
-- Indexes for table `sendmessageinfo`
--
ALTER TABLE `sendmessageinfo`
  ADD PRIMARY KEY (`SendTime`,`SenderID`,`PostsID`);

--
-- Indexes for table `signed`
--
ALTER TABLE `signed`
  ADD PRIMARY KEY (`SID`,`recordID`),
  ADD KEY `recordID` (`recordID`);

--
-- Indexes for table `signrecord`
--
ALTER TABLE `signrecord`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `houseparentID` (`houseparentID`);

--
-- Indexes for table `stayinfo`
--
ALTER TABLE `stayinfo`
  ADD PRIMARY KEY (`stayID`);

--
-- Indexes for table `userinfo`
--
ALTER TABLE `userinfo`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `userinfoh`
--
ALTER TABLE `userinfoh`
  ADD PRIMARY KEY (`ID`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `announcement`
--
ALTER TABLE `announcement`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 使用表AUTO_INCREMENT `departinfo`
--
ALTER TABLE `departinfo`
  MODIFY `departID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- 使用表AUTO_INCREMENT `postsinfo`
--
ALTER TABLE `postsinfo`
  MODIFY `PostsID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- 使用表AUTO_INCREMENT `repairinfo`
--
ALTER TABLE `repairinfo`
  MODIFY `ApplyID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- 使用表AUTO_INCREMENT `signrecord`
--
ALTER TABLE `signrecord`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- 使用表AUTO_INCREMENT `stayinfo`
--
ALTER TABLE `stayinfo`
  MODIFY `stayID` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- 限制导出的表
--

--
-- 限制表 `announcement`
--
ALTER TABLE `announcement`
  ADD CONSTRAINT `announcement_ibfk_1` FOREIGN KEY (`houseparentID`) REFERENCES `userinfoh` (`ID`);

--
-- 限制表 `signrecord`
--
ALTER TABLE `signrecord`
  ADD CONSTRAINT `signrecord_ibfk_1` FOREIGN KEY (`houseparentID`) REFERENCES `userinfoh` (`ID`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
