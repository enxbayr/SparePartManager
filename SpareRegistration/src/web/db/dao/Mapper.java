package web.db.dao;

import java.util.List;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import web.db.model.Department;
import web.db.model.Location;
import web.db.model.Moving;
import web.db.model.Spare;
import web.db.model.Status;
import web.db.model.Systems;
import web.db.model.Ttype;
import web.db.model.Unit;
import web.db.model.User;

public interface Mapper {
	
	//ADMIN
	@Select("select * from t_user where c_name=#{c_name} and c_pass=#{c_pass}")
	User isOperator(User o);
	
	@Select("select u.*, d.c_name as dep_name from t_user u, t_dep d where u.dep_id=d.id order by u.c_name")
	List<User> getAllUsers();
	
	@Select("select u.*, d.c_name as dep_name from t_user u, t_dep d where u.dep_id=d.id and u.c_role!=0 and u.dep_id=#{dep_id} order by u.c_name")
	List<User> getUsers(@Param("dep_id") int dep_id);
	
	@Select("select u.*, d.c_name as dep_name from t_user u, t_dep d where u.dep_id=d.id and u.id=#{id}")
	User getUser(@Param("id") int id);
	
	@Insert("insert into t_user(c_name, c_pass, c_role, c_date, dep_id) " +
			"values(#{c_name}, #{c_pass}, #{c_role}, now(), #{dep_id})")
	int addUser(User op);
	
	@Delete("delete from t_user where id=#{id}")
	int removeUser(@Param("id") int id);
	
	@Update("update t_user set c_role=#{c_role}, dep_id=#{dep_id} where id=#{id}")
	int updateUserPermission(User op);
	
	@Update("update t_user set c_pass=#{c_pass} where id=#{id}")
	int updateUserPass(User op);
	
	//SYSTEM
	@Select("select a.*, b.c_name as depName from t_system a, t_dep b where a.dep_id=b.id order by a.c_name, a.c_vendor")
	List<Systems> getSystems();
	
	@Select("select a.*, b.c_name as depName from t_system a, t_dep b where a.dep_id=b.id and a.dep_id=#{dep_id} order by a.c_name, a.c_vendor")
	List<Systems> getSystemsByDep(@Param("dep_id") long dep_id);
	
	@Select("select count(*) from t_system")
	int getSystemCount();
	
	@Select("select a.*, b.c_name as depName from t_system a, t_dep b where a.dep_id=b.id order by a.c_name, a.c_vendor limit #{p}, #{l}")
	List<Systems> getSystemsByPage(@Param("p") int p, @Param("l") int l);
	
	@Select("select count(*) from t_system where dep_id=#{dep_id}")
	int getSystemsByPageCountD(@Param("dep_id") int dep_id);

	@Select("select a.*, b.c_name as depName from t_system a, t_dep b where a.dep_id=b.id and a.dep_id=#{dep_id} order by a.c_name, a.c_vendor limit #{p}, #{l}")
	List<Systems> getSystemsByPageD(@Param("dep_id") int dep_id, @Param("p") int p, @Param("l") int l);

	@Insert("insert into t_system(c_name, c_vendor, c_desc, dep_id) " +
			"values(#{c_name}, #{c_vendor}, #{c_desc}, #{dep_id})")
	int addSystem(Systems u);

	@Delete("delete from t_system where id=#{id}")
	int removeSystem(@Param("id") long id);
	
	@Update("update t_system set c_name=#{c_name}, c_vendor=#{c_vendor}, c_desc=#{c_desc}, dep_id=#{dep_id} " +
			"where id=#{id}")
	int updateSystem(Systems u);
	
	@Select("select * from t_system where id = #{id}")
	Systems getSystemById(@Param("id") long id);
	
	//DEPARTMENT
	@Select("select * from t_dep order by c_name")
	List<Department> getDepartments();
	
	@Select("select count(*) from t_dep")
	int getDepCount();
	
	@Select("select * from t_dep order by c_name limit #{p}, #{l}")
	List<Department> getDepByPage(@Param("p") int p, @Param("l") int l);
	
	@Insert("insert into t_dep(c_name) values(#{c_name})")
	int addDep(Department t);

	@Delete("delete from t_dep where id=#{id}")
	int removeDep(@Param("id") long id);
	
	@Update("update t_dep set c_name=#{c_name} " +
			"where id=#{id}")
	int updateDep(Department u);
	
	@Select("select * from t_dep where id = #{id}")
	Department getDepById(@Param("id") int id);
	
	//UNIT
	@Select("select count(*) from t_unit")
	int getUnitCount();
	
	@Select("select count(*) from t_unit where type_id=#{id}")
	int getUnitCountById(@Param("id") long id);
	
	@Select("select a.*, b.c_name as type_name from t_unit a, t_type b where a.type_id=b.id order by c_name")
	List<Unit> getUnits();
	
	@Select("select * from t_unit where id=#{id}")
	Unit getUnitById(@Param("id") long id);
	
	@Select("select a.*, b.c_name as type_name from t_unit a, t_type b where a.type_id=b.id and a.type_id=#{id} order by a.c_name limit #{p}, #{l}")
	List<Unit> getUnitsByType(@Param("id") long id, @Param("p") int p, @Param("l") int l);
	
	@Select("select a.*, b.c_name as type_name from t_unit a, t_type b where a.type_id=b.id limit #{p}, #{l}")
	List<Unit> getUnit(@Param("p") int p, @Param("l") int l);
	
	@Insert("insert into t_unit(c_name, c_desc, type_id) " +
			"values(#{c_name}, #{c_desc}, #{type_id})")
	int addUnit(Unit u);
	
	@Delete("delete from t_unit where id=#{id}")
	int removeUnit(@Param("id") long id);
	
	@Update("update t_unit set c_name=#{c_name}, c_desc=#{c_desc}, type_id=#{type_id} " +
			"where id=#{id}")
	int updateUnit(Unit u);

	//LOCATION
	@Select("select count(*) from t_location")
	int getLocationCount();
	
	@Select("select * from t_location order by c_building, c_room, c_rack limit #{p}, #{l}")
	List<Location> getLocationsByPage(@Param("p") int p, @Param("l") int l);
	
	@Select("select * from t_location order by c_building, c_room, c_rack")
	List<Location> getLocations();
	
	@Insert("insert into t_location(c_building, c_room, c_rack) " +
			"values(#{c_building}, #{c_room}, #{c_rack})")
	int addLocation(Location u);

	@Delete("delete from t_location where id=#{id}")
	int removeLocation(@Param("id") long id);
	
	@Update("update t_location set c_building=#{c_building}, c_room=#{c_room}, c_rack=#{c_rack} " +
			"where id=#{id}")
	int updateLocation(Location u);
	
	@Select("select * from t_location where id = #{id}")
	Location getLocation(@Param("id") long id);
	
	//STATUS
	@Select("select * from t_status order by id")
	List<Status> getStatuses();
	
	//TYPE
	@Select("select * from t_type order by c_name")
	List<Ttype> getTypes();
	
	@Select("select count(*) from t_Type")
	int getTypeCount();
	
	@Select("select * from t_type order by c_name limit #{p}, #{l}")
	List<Ttype> getTypesByPage(@Param("p") int p, @Param("l") int l);
	
	@Insert("insert into t_type(c_name) values(#{c_name})")
	int addType(Ttype t);

	@Delete("delete from t_type where id=#{id}")
	int removeType(@Param("id") long id);
	
	@Update("update t_type set c_name=#{c_name} " +
			"where id=#{id}")
	int updateType(Ttype u);
	
	@Select("select * from t_type where id = #{id}")
	Ttype getTypeById(@Param("id") int id);
	

	//SPARE
	@Select("select sp.status_id, st.status as description, count(*) as asset_id from t_spare sp, t_status st where sp.status_id = st.id group by sp.status_id")
	List<Spare> getSpareByStatus();
	
	@Select("select sp.status_id, st.status as description, count(*) as asset_id from t_spare sp, t_status st where sp.status_id = st.id and sp.dep_id=#{dip} group by sp.status_id")
	List<Spare> getSpareByStatusD(@Param("dip") int dip);
	
	@Select("select t.*, concat(l.c_building,' (',l.c_room,') ' ,l.c_rack) as locName from t_spare t, t_location l where t.id=#{id} and t.loc_id=l.id")
	Spare getSpareById(@Param("id") long id);
	
	@Select("select s.*, un.c_name as unitName, concat(sy.c_name,' ',sy.c_vendor) as sysName, d.c_name as depName, concat(l.c_building,' (',l.c_room,') ' ,l.c_rack) as locName, st.status as statName from t_spare s" +
	" join t_unit un on s.unit_id=un.id" +
	" join t_system sy on s.sys_id=sy.id" +
	" join t_dep d on s.dep_id=d.id" +
	" join t_location l on s.loc_id=l.id" +
	" join t_status st on s.status_id=st.id" +
	" where s.id=#{id}")
	Spare getSpareByIdT(@Param("id") long id);
	
	/*
	 * 
	 * @Select("select count(*) from t_spare")
	int getSpareCount();
	*@Select("select * from t_spare limit #{p}, #{l}")
	List<Spare> getSpare(@Param("p") int p, @Param("l") int l);
	
	
	@Select("select s.*, un.c_name as unitName,(select ty.c_name from t_type ty where ty.id=un.type_id) as unitType, concat(sy.c_name,' ',sy.c_vendor) as sysName, d.c_name as depName, concat(l.c_building,' (',l.c_room,') ' ,l.c_rack) as locName, us.c_name as userName, st.status as statName from t_spare s" +
	" join t_unit un on s.unit_id=un.id" +
	" join t_system sy on s.sys_id=sy.id" +
	" join t_dep d on s.dep_id=d.id" +
	" join t_location l on s.loc_id=l.id" +
	" join t_user us on s.user_id=us.id" +
	" join t_status st on s.status_id=st.id" +
	" limit #{p}, #{l}")
	List<Spare> getSpare(@Param("p") int p, @Param("l") int l);
	*/
	
	@Select("select s.*, un.c_name as unitName, ty.c_name as unitType, concat(sy.c_name,' ',sy.c_vendor) as sysName, d.c_name as depName, concat(l.c_building,' (',l.c_room,') ' ,l.c_rack) as locName, st.status as statName from t_spare s" +
			" join t_unit un on s.unit_id=un.id" +
			" join t_system sy on s.sys_id=sy.id" +
			" join t_dep d on s.dep_id=d.id" +
			" join t_location l on s.loc_id=l.id" +
			" join t_status st on s.status_id=st.id" +
			" join t_type ty on ty.id=un.type_id" +
			" ${query}" +
			" order by s.c_date desc")
	List<Spare> getSpareFilterXLS(@Param("query") String query);
	
	@Select("select s.*, un.c_name as unitName, ty.c_name as unitType, concat(sy.c_name,' ',sy.c_vendor) as sysName, d.c_name as depName, concat(l.c_building,' (',l.c_room,') ' ,l.c_rack) as locName, st.status as statName from t_spare s" +
			" join t_unit un on s.unit_id=un.id" +
			" join t_system sy on s.sys_id=sy.id" +
			" join t_dep d on s.dep_id=d.id" +
			" join t_location l on s.loc_id=l.id" +
			" join t_status st on s.status_id=st.id" +
			" join t_type ty on ty.id=un.type_id" +
			" ${query}" +
			" order by s.c_date desc" +
			" limit #{p}, #{l}")
	List<Spare> getSpareFilter(@Param("query") String query, @Param("p") int p, @Param("l") int l);
	
	@Select("select count(*) from t_spare s" +
			" join t_unit un on s.unit_id=un.id" +
			" join t_system sy on s.sys_id=sy.id" +
			" join t_dep d on s.dep_id=d.id" +
			" join t_location l on s.loc_id=l.id" +
			" join t_status st on s.status_id=st.id" +
			" ${query}")
	int getSpareFilterCount(@Param("query") String query);
	
	@Insert("insert into t_spare(unit_id, sys_id, dep_id, loc_id, c_date, serial_key, product_num, description, user_name, status_id, asset_id) " +
			" values(#{unit_id}, #{sys_id}, #{dep_id}, 1, now(), #{serial_key}, #{product_num}, #{description}, #{user_name}, 1, #{asset_id})")
	int addSpare(Spare sp);
	
	@Delete("delete from t_spare where id=#{id}")
	int removeSpare(@Param("id") long id);
	
	@Update("update t_spare set unit_id=#{unit_id}, sys_id=#{sys_id}, dep_id=#{dep_id}, loc_id=#{loc_id}, " +
			"serial_key=#{serial_key}, product_num=#{product_num}, description=#{description}, " +
			"user_name=#{user_name}, status_id=#{status_id}, asset_id=#{asset_id} " +
			"where id=#{id}")
	int updateSpare(Spare sp);
	
	@Update("update t_spare set loc_id=#{loc_id}, status_id=#{status_id} where id=#{id}")
	int updateSpareAfterMoving(@Param("loc_id") long loc_id, @Param("status_id") long status_id, @Param("id") long id);
	
	//MOVING
	@Select("select count(*) from t_moving")
	int getMovingCount();
	
	@Select("select count(*) from t_moving where dep_id=#{depid}")
	int getMovingCountByDep(@Param("depid") int depid);
	
	@Select("select count(*) from t_moving where c_date between #{date1} and #{date2}")
	int getMovingCountByDate(@Param("date1") String date1, @Param("date2") String date2);
	
	@Select("select count(*) from t_moving where dep_id=#{depid} and c_date between #{date1} and #{date2}")
	int getMovingCountByDepDate(@Param("depid") int depid, @Param("date1") String date1, @Param("date2") String date2);
	
	@Insert("insert into t_moving(spare_id, c_date, user_name, comment, loc_from, loc_to, dep_id, status_from_id, status_to_id) " +
			" values(#{spare_id}, now(), #{user_name}, #{comment}, #{loc_from}, #{loc_to}, #{dep_id}, #{status_from_id}, #{status_to_id})")
	int addMoving(Moving mv);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"order by m.c_date desc")
	List<Moving> getMovingXLS();
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"order by m.c_date desc "+ 
			" limit #{p}, #{l}")
	List<Moving> getMoving(@Param("p") int p, @Param("l") int l);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.dep_id=#{depid} " +
			"and m.c_date between #{date1} and #{date2} " +
			"order by m.c_date desc")
	List<Moving> getMovingByDepDateXLS(@Param("depid") int depid, @Param("date1") String date1, @Param("date2") String date2);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.dep_id=#{depid} " +
			"and m.c_date between #{date1} and #{date2} " +
			"order by m.c_date desc "+ 
			" limit #{p}, #{l}")
	List<Moving> getMovingByDepDate(@Param("depid") int depid, @Param("date1") String date1, @Param("date2") String date2, @Param("p") int p, @Param("l") int l);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.c_date between #{date1} and #{date2} " +
			"order by m.c_date desc")
	List<Moving> getMovingByDateXLS(@Param("date1") String date1, @Param("date2") String date2);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.c_date between #{date1} and #{date2} " +
			"order by m.c_date desc "+ 
			" limit #{p}, #{l}")
	List<Moving> getMovingByDate(@Param("date1") String date1, @Param("date2") String date2, @Param("p") int p, @Param("l") int l);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.dep_id=#{depid} " +
			"order by m.c_date desc")
	List<Moving> getMovingByDepXLS(@Param("depid") int depid);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.dep_id=#{depid} " +
			"order by m.c_date desc "+ 
			" limit #{p}, #{l}")
	List<Moving> getMovingByDep(@Param("depid") int depid, @Param("p") int p, @Param("l") int l);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			"where m.spare_id=#{spare_id } "+
			"order by m.c_date desc")
	List<Moving> getMovingHistory(@Param("spare_id")long spare_id);
	
	@Select("select m.*, uu.c_name as spareName, s.serial_key as serialKey, concat(lf.c_building,' (',lf.c_room,') ' ,lf.c_rack) as locFrom, concat(lt.c_building,' (',lt.c_room,') ' ,lt.c_rack) as locTo, stf.status as statFrom, stt.status as statTo, d.c_name as depName from t_moving m "+
			"join t_spare s on m.spare_id=s.id "+
			"left join t_location lf on m.loc_from=lf.id "+
			"left join t_location lt on m.loc_to=lt.id "+
			"left join t_status stf on m.status_from_id=stf.id "+
			"left join t_status stt on m.status_to_id=stt.id "+
			"join t_dep d on m.dep_id=d.id "+
			"join t_unit uu on uu.id=s.unit_id "+
			" where m.id=#{id}")
	Moving getMovingById(@Param("id") long id);
	
	//SPARE COUNT
	@Select("select count(distinct(unit_id)) from t_spare")
	int getSpareCountC();
	
	@Select("select u.id as unit_id, u.c_name as unitName, count(*) as description from t_spare s " +
			"inner join t_unit u on s.unit_id = u.id group by s.unit_id " +
			"limit #{p}, #{l}")
	List<Spare> getSpareCount(@Param("p") int p, @Param("l") int l);
	
	@Select("select count(distinct(unit_id)) from t_spare where dep_id=#{dep_id}")
	int getSpareCountByDepC(@Param("dep_id") long dep_id);
	
	@Select("select u.id as unit_id, u.c_name as unitName, count(*) as description from t_spare s " +
			"inner join t_unit u on s.unit_id = u.id where s.dep_id=#{dep_id} group by s.unit_id " +
			"limit #{p}, #{l}")
	List<Spare> getSpareCountByDep(@Param("dep_id") long dep_id, @Param("p") int p, @Param("l") int l);
		
	@Select("select st.id as id, st.status as status, count(*) as count from t_spare s inner join t_status st on s.status_id=st.id where unit_id=#{id} group by status_id")
	List<Status> getSpareCountByStatus(@Param("id") long id);
	
	@Select("select st.id as id, st.status as status, count(*) as count from t_spare s inner join t_status st on s.status_id=st.id where unit_id=#{id} and dep_id=#{dep_id} group by status_id")
	List<Status> getSpareCountByStatusByDep(@Param("dep_id") long dep_id, @Param("id") long id);
	
	/*
	 * select t1.*, t2.c_name, t3.c_name, t4.c_rack, t5.c_name, t6.c_name, t7.status from t_spare t1 
inner join t_system t2 on t1.sys_id = t2.id 
inner join t_unit t3 on t1.unit_id = t3.id
inner join t_location t4 on t1.loc_id = t4.id
inner join t_dep t5 on t1.dep_id = t5.id
inner join t_user t6 on t1.user_id = t6.id 
inner join t_status t7 on t1.status_id = t7.id limit 0, 5
	 */
}