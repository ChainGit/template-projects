package com.chain.project.test.handlers;

import com.chain.project.base.handlers.BaseHandler;
import com.chain.project.common.directory.Constant;
import com.chain.project.common.domain.JsonMap;
import com.chain.project.common.domain.Result;
import com.chain.project.common.utils.JyComUtils;
import com.chain.project.test.entities.Person;
import com.chain.project.test.service.PersonService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模式：
 * request带参数，response带值
 * request带参数，response不带值
 * request不带参数，response带值
 * request不带参数，response不带值
 * <p>
 * 规定：
 * 1、数据到Controller时一定是有的，即使没有数据，也会创建一个空的JsonMap。
 * 2、返回的Response也一定也是有数据，即使没有返回的数据，也会创建一个空的Result，<b>但仅限方法返回的是Result</b>。
 * 3、如果总的加密配置为false,那么Result中的加密会被忽略，如果总的加密设置为true，那么会根据Result中的加密设置进行加密（默认为true，加密）
 * <p>
 * 约定：
 * 1、加密的数据参数key为"s"，不加密的数据参数key为"ns".
 * 2、先处理s，再处理ns，若s不为空，则ns不再处理.
 */
//测试访问的URL目录是test,均为POST方法
@RequestMapping(value = "/test", method = RequestMethod.POST)
//相当于Controller+ResponseBody
@RestController
public class PersonHandler extends BaseHandler {

    private static Logger logger = LoggerFactory.getLogger(PersonHandler.class);

    /////////////////////////////////////////////////////////////
    /***以下测试分别在dev和test中进行了测试，prod环境不加载test功能***/
    /////////////////////////////////////////////////////////////

    @Autowired
    private PersonService personService;

    /***根据配置文件决定是否启用加密的机制***/

    //返回加密数据测试
    @RequestMapping("/encrypt")
    public Result encrypt(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        System.out.println(jsonMap);
        // throw new RuntimeException("发生错误了1");
        // return null;
        return Result.ok(personService.queryListAll(), Result.SUCCESS);
    }

    //返回不加密数据测试
    @RequestMapping("/plain")
    public Result plain(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        System.out.println(jsonMap);
        // throw new RuntimeException("发生错误了2");
        // return null;
        return Result.ok(personService.queryListAll(), Result.SUCCESS).setEncrypt(Constant.RESPONSE_PLAIN_JSON_KEY);
    }

    /***增删改查、分页查询、事务（已配置自动开启）的测试***/
    /***注意1：返回均为加密的数据***/
    /***注意2：因为是纯后台，所以采用非REST风格，即所有参数以JSON形式放在body里，且均为POST方式***/

    //查找(规范为GET，这里使用POST)
    @RequestMapping("/get")
    public Result get(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        System.out.println(jsonMap);
        //Jackson会自动转换
        int id = (int) jsonMap.get("id");
        Person person = personService.findById(id);
        return Result.ok(person, Constant.SUCCESS).setEncrypt(Constant.RESPONSE_ENCRYPT_JSON_KEY);
    }

    //更新
    @RequestMapping("/update")
    public Result update(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        System.out.println(jsonMap);
        Person person = JyComUtils.mapToObject(jsonMap, Person.class);
        int num = personService.update(person);
        return Result.ok(num, Result.SUCCESS);
    }

    //增加
    @RequestMapping("/add")
    public Result add(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        System.out.println(jsonMap);
        Person person = JyComUtils.mapToObject(jsonMap, Person.class);
        int num = personService.insert(person);
        return Result.ok(num, Result.SUCCESS);
    }

    //删除
    @RequestMapping("/delete")
    public Result delete(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        System.out.println(jsonMap);
        int id = jsonMap.get("id", Integer.class);
        int num = personService.deleteById(id);
        return Result.ok(num, Result.SUCCESS);
    }

    //分页查询(使用PageHelper)
    @RequestMapping(value = "/page")
    public Result page(@RequestAttribute(Constant.JSON_MAP) JsonMap jsonMap) {
        int page = jsonMap.getInteger(Constant.CURRENT_PAGE);
        int rows = jsonMap.getInteger(Constant.EACH_PAGE_ROWS);
        PageInfo<Person> pageInfo = personService.getPage(page, rows);
        JsonMap outMap = new JsonMap();
        outMap.put(Constant.EACH_PAGE_RECORDS, pageInfo.getList());
        outMap.put(Constant.TOTAL_PAGES, pageInfo.getPages());
        outMap.put(Constant.TOTAL_RECORDS, pageInfo.getTotal());
        return Result.ok(outMap, Result.SUCCESS).setEncrypt(false);
    }

}
