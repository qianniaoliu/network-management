package com.network.management.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yusheng
 */
public class LocomotiveServiceByNewCoreNetTests {

    @Test
    public void testResolve(){
        String htmlContent = "<html xml:lang=\"zh-hans\" lang=\"zh-hans\">\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"description\" content=\"\">\n" +
                "    <meta name=\"author\" content=\"\">\n" +
                "    <title>IPLOOK 4G Web OMC</title>\n" +
                "    <link href=\"/static/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/static/css/font-awesome.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/static/css/all.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\">\n" +
                "    <link href=\"/static/css/sourceHanSans.css\" rel=\"stylesheet\" type=\"text/css\" media=\"all\">\n" +
                "    <link rel=\"stylesheet\" href=\"/static/css/styleNew.css\" type=\"text/css\" media=\"all\">\n" +
                "    <link href=\"/static/css/bootstrap-multiselect.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/static/css/bootstrap-switch.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/static/css/awesome-bootstrap-checkbox.css\" rel=\"stylesheet\" media=\"all\">\n" +
                "    <link href=\"/static/css/bootstrap-datetimepicker.min.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    <link href=\"/static/css/cxcalendar.css\" rel=\"stylesheet\" type=\"text/css\">\n" +
                "    \n" +
                "<style>\n" +
                "    span.multiselect-native-select{\n" +
                "        display: inline-block;\n" +
                "        margin-bottom: -12px;\n" +
                "    }\n" +
                "</style>\n" +
                "\n" +
                "    <style>\n" +
                "        #page-top {\n" +
                "            background: #fff;\n" +
                "        }\n" +
                "\n" +
                "        #alarmStorageTimeForm table tr:nth-last-of-type(1) {\n" +
                "            display: none;\n" +
                "        }\n" +
                "\n" +
                "        #alarmStorageTimeForm table tr:nth-last-of-type(1)>td {\n" +
                "            padding-top: 5px;\n" +
                "        }\n" +
                "\n" +
                "        .percentage {\n" +
                "            position: relative;\n" +
                "        }\n" +
                "\n" +
                "        .percentage::before {\n" +
                "            content: '%';\n" +
                "            display: block;\n" +
                "            position: absolute;\n" +
                "            right: 10px;\n" +
                "            top: 7px;\n" +
                "        }\n" +
                "        .accordion{\n" +
                "            overflow-x: hidden;\n" +
                "            overflow-y: auto;\n" +
                "            height: 100vh;\n" +
                "        }\n" +
                "        /*#accordion::-webkit-scrollbar {*/\n" +
                "        /*    width: 2px;*/\n" +
                "        /*}*/\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body id=\"page-top\">\n" +
                "    <!--Sidebar-->\n" +
                "    <ul class=\"navbar-nav bg-gradient-primary sidebar sidebar-dark accordion\" id=\"accordion\">\n" +
                "        <!-- Sidebar - Brand -->\n" +
                "        <a class=\"sidebar-brand d-flex\" href=\"/home.html\" style=\"display:block !important;text-align: center\">\n" +
                "            <img src=\"/static/images/logo/logo_3.png\" class=\"img-logo\" alt=\"Responsive image\">\n" +
                "        </a>\n" +
                "        <!-- Configuration -->\n" +
                "        \n" +
                "        \n" +
                "        <li class=\"nav-item\">\n" +
                "            <a role=\"button\" class=\"nav-link collapsed\" id=\"udmcollapsed2\" href=\"#\" data-toggle=\"collapse\"\n" +
                "                data-target=\"#configurationCollapse\" aria-expanded=\"false\" aria-controls=\"configurationCollapse\">\n" +
                "                <div class=\"top-menu-item\">\n" +
                "                    <div class=\"top-menu-item-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574307201223\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"22440\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M1008.352978 414.758078l-99.257796-25.726392c-7.615524-24.702456-17.406912-48.572964-29.310168-71.163552l51.900756-88.250484c4.7997-8.127492 3.455784-18.430848-3.135804-25.086432l-109.177176-109.241172c-6.71958-6.655584-17.022936-7.935504-25.150428-3.1998l-88.31448 51.900756c-22.526592-11.83926-46.333104-21.630648-71.099556-29.310168L609.209924 15.423036C606.842072 6.335604 598.586588 0 589.179176 0L434.756828 0C425.413412 0 417.157928 6.335604 414.790076 15.423036L388.999688 114.744828C364.297231 122.424348 340.554715 132.215737 318.092119 144.118993L229.777639 92.090244C221.586151 87.35454 211.218799 88.570464 204.691207 95.290044L95.386038 204.531217C88.730454 211.186801 87.386538 221.554153 92.250234 229.553653l51.964752 88.378476C132.311731 340.394725 122.584338 364.201237 114.840822 388.903694L15.51903 414.694082C6.367602 416.997938 0.031998 425.253422 0.031998 434.660834l0 154.422349c0 9.407412 6.335604 17.662896 15.487032 19.966752l99.257796 25.726392c7.67952 24.830448 17.534904 48.63696 29.310168 71.03556l-52.028748 88.31448c-4.735704 8.127492-3.455784 18.430848 3.1998 25.086432l109.177176 109.241172c6.71958 6.655584 17.022936 7.935504 25.150428 3.135804l88.31448-51.964752c22.526592 11.83926 46.333104 21.75864 71.03556 29.310168l25.662396 99.321792c2.495844 9.215424 10.751328 15.615024 20.15874 15.615024l154.422349 0c9.407412 0 17.5989-6.335604 19.966752-15.487032l25.790388-99.321792c24.766452-7.67952 48.572964-17.470908 71.099556-29.310168l88.186488 51.964752c8.191488 4.671708 18.430848 3.455784 25.086432-3.1998l109.241172-109.11318c6.71958-6.655584 7.9995-16.95894 3.1998-25.086432l-51.964752-88.31448c11.967252-22.526592 21.75864-46.333104 29.374164-71.03556l99.257796-25.726392C1017.632398 606.746078 1023.968002 598.490594 1023.968002 589.083182L1023.968002 434.660834C1023.84001 425.381414 1017.504406 417.061934 1008.352978 414.758078L1008.352978 414.758078 1008.352978 414.758078 1008.352978 414.758078zM512 738.513843c-124.984188 0-226.545841-101.625648-226.545841-226.545841S387.079808 285.358165 512 285.358165c124.920192 0 226.481845 101.625648 226.481845 226.545841S636.856196 738.513843 512 738.513843L512 738.513843 512 738.513843z\" p-id=\"22441\" fill=\"#ffffff\"></path></svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"top-menu-item-text\">\n" +
                "                        <span>配置</span>\n" +
                "                    </div>\n" +
                "                    <div class=\"right-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"down-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </a>\n" +
                "            <div id=\"configurationCollapse\" class=\"collapse\" aria-labelledby=\"headingConfiguration\"\n" +
                "                data-parent=\"#accordion\">\n" +
                "                <div class=\"collapse-inner\">\n" +
                "                    <ul class=\"navbar-nav bg-gradient-secondary\" id=\"configSidebar\">\n" +
                "\n" +
                "                        \n" +
                "                        \n" +
                "                        <li class=\"nav-item\">\n" +
                "                            <a role=\"button\" id=\"gwcollapsed\" class=\"nav-link collapsed\" href=\"#\"\n" +
                "                                data-toggle=\"collapse\" data-target=\"#collapsegw\" aria-expanded=\"false\"\n" +
                "                                aria-controls=\"collapsegw\">\n" +
                "                                <div class=\"second-menu-item\">\n" +
                "                                    <div class=\"second-menu-item-text\"><span>GW</span></div>\n" +
                "                                    <div class=\"right-icon\">\n" +
                "                                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"down-icon\">\n" +
                "                                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </a>\n" +
                "                            <div id=\"collapsegw\" class=\"collapseConfiguration_show_gw collapse\"\n" +
                "                                aria-labelledby=\"headinggw\" data-parent=\"#configSidebar\">\n" +
                "                                <div class=\"collapse-inner\">\n" +
                "                                    <ul class=\"innermost-menu bg-gradient-tertiary\">\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <li><a class=\"collapse-item\" href=\"/gw/gwIndex.html?name=gw01\"\n" +
                "                                                data-name=\"gw01\" title=\"gw01\"\n" +
                "                                                onclick=\"assigment(this)\">&bull;&nbsp;gw01</a></li>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                    </ul>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </li>\n" +
                "                        \n" +
                "                        \n" +
                "                        \n" +
                "                        <li class=\"nav-item\">\n" +
                "                            <a role=\"button\" id=\"mmecollapsed\" class=\"nav-link collapsed\" href=\"#\"\n" +
                "                                data-toggle=\"collapse\" data-target=\"#collapsemme\" aria-expanded=\"false\"\n" +
                "                                aria-controls=\"collapsemme\">\n" +
                "                                <div class=\"second-menu-item\">\n" +
                "                                    <div class=\"second-menu-item-text\"><span>MME</span></div>\n" +
                "                                    <div class=\"right-icon\">\n" +
                "                                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"down-icon\">\n" +
                "                                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </a>\n" +
                "                            <div id=\"collapsemme\" class=\"collapseConfiguration_show_mme collapse\"\n" +
                "                                aria-labelledby=\"headingmme\" data-parent=\"#configSidebar\">\n" +
                "                                <div class=\"collapse-inner\">\n" +
                "                                    <ul class=\"innermost-menu bg-gradient-tertiary\">\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <li><a class=\"collapse-item\" href=\"/mme/mmeIndex.html?name=mme01\"\n" +
                "                                                data-name=\"mme01\" title=\"mme01\"\n" +
                "                                                onclick=\"assigment(this)\">&bull;&nbsp;mme01</a></li>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                    </ul>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </li>\n" +
                "                        \n" +
                "                        \n" +
                "                        \n" +
                "                        \n" +
                "                        \n" +
                "                        <li class=\"nav-item\">\n" +
                "                            <a role=\"button\" id=\"udmcollapsed\" class=\"nav-link collapsed\" href=\"#\"\n" +
                "                                data-toggle=\"collapse\" data-target=\"#collapseudm\" aria-expanded=\"false\"\n" +
                "                                aria-controls=\"collapseudm\">\n" +
                "                                <div class=\"second-menu-item\">\n" +
                "                                    <div class=\"second-menu-item-text\"><span>UDM</span></div>\n" +
                "                                    <div class=\"right-icon\">\n" +
                "                                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                                    </div>\n" +
                "                                    <div class=\"down-icon\">\n" +
                "                                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                                    </div>\n" +
                "                                </div>\n" +
                "                            </a>\n" +
                "                            <div id=\"collapseudm\" class=\"collapseConfiguration_show_udm collapse\"\n" +
                "                                aria-labelledby=\"headingudm\" data-parent=\"#configSidebar\">\n" +
                "                                <div class=\"collapse-inner\">\n" +
                "                                    <ul class=\"innermost-menu bg-gradient-tertiary\">\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <li><a class=\"collapse-item\" href=\"/udm/udmIndex.html?name=hss01\"\n" +
                "                                                data-name=\"hss01\" title=\"hss01\"\n" +
                "                                                onclick=\"assigment(this)\">&bull;&nbsp;hss01</a></li>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                    </ul>\n" +
                "                                </div>\n" +
                "                            </div>\n" +
                "                        </li>\n" +
                "                        \n" +
                "                        \n" +
                "                    </ul>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </li>\n" +
                "        \n" +
                "\n" +
                "        <!-- System -->\n" +
                "        <li class=\"nav-item\">\n" +
                "            <a role=\"button\" id=\"systemremove\" class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\"\n" +
                "                data-target=\"#collapseSystem\" aria-expanded=\"false\" aria-controls=\"collapseSystem\">\n" +
                "                <div class=\"top-menu-item\">\n" +
                "                    <div class=\"top-menu-item-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308360691\" class=\"icon\" viewBox=\"0 0 1025 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"27417\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M360.806499 587.464526 73.613373 587.464526c-20.675212 0-37.984693 7.164202-52.216932 21.492605C7.164202 623.237451 0 640.546931 0 661.222144l0 289.020238c0 20.627131 7.164202 38.080856 21.396441 52.313096 14.232239 14.232239 31.541719 21.396441 52.216932 21.396441l287.193126 0c20.627131 0 38.225102-7.068038 52.93816-21.396441 14.520731-14.232239 21.781096-31.637883 21.781096-52.313096l0-289.020238c0-20.675212-7.212283-37.984693-21.781096-52.313096C399.031601 594.724891 381.433629 587.464526 360.806499 587.464526zM360.806499 59.910034 73.613373 59.910034c-20.675212 0-37.984693 7.212283-52.216932 21.444523C7.164202 95.586796 0 113.040522 0 133.715735l0 287.81819c0 20.627131 7.164202 38.41743 21.396441 52.93816 14.232239 14.713058 31.541719 21.973423 52.216932 21.973423l287.193126 0c20.627131 0 38.225102-7.260365 52.93816-21.973423 14.520731-14.616894 21.781096-32.31103 21.781096-52.93816L435.525755 133.715735c0-20.723294-7.212283-38.080856-21.781096-52.40926C399.031601 67.122318 381.433629 59.910034 360.806499 59.910034zM888.312908 587.464526l-287.241208 0c-20.530967 0-38.225102 7.164202-52.841997 21.492605-14.616894 14.328403-22.021505 31.637883-22.021505 52.313096l0 289.020238c0 20.627131 7.404611 38.080856 22.021505 52.313096 14.713058 14.232239 32.214866 21.396441 52.841997 21.396441l287.241208 0c20.530967 0 37.984693-7.068038 52.216932-21.396441 14.136076-14.232239 21.252195-31.637883 21.252195-52.313096l0-289.020238c0-20.675212-7.019956-38.080856-21.252195-52.313096C926.345682 594.724891 908.843875 587.464526 888.312908 587.464526zM1002.026577 224.830915l-203.049819-203.049819C784.359863 7.11612 767.002301 0 746.855989 0c-20.146312 0-37.551956 7.260365-52.16885 21.925342l-203.482556 203.482556c-14.568813 14.568813-22.069587 32.214866-22.309997 52.553505-0.336573 20.434803 6.779546 37.840447 21.396441 52.457341l203.049819 203.049819c14.568813 14.568813 32.166784 21.87726 52.986242 21.87726 20.627131-0.048082 38.225102-7.452693 52.793915-22.021505l203.482556-203.482556c14.664976-14.664976 21.829178-32.07062 21.63685-52.457341C1023.951918 256.949617 1016.595389 239.399728 1002.026577 224.830915z\" p-id=\"27418\" fill=\"#ffffff\"></path></svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"top-menu-item-text\">\n" +
                "                        <span>系统</span>\n" +
                "                    </div>\n" +
                "                    <div class=\"right-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"down-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </a>\n" +
                "            <div id=\"collapseSystem\" class=\"collapse\" aria-labelledby=\"headingSystem\" data-parent=\"#accordion\">\n" +
                "                <div class=\"bg-gradient-tertiary py-2 collapse-inner rounded\">\n" +
                "                    <a class=\"collapse-item\" href=\"/deviceMgmt.html\">设备管理</a>\n" +
                "                    \n" +
                "                    <a class=\"collapse-item\" href=\"/configImportAndExport.html\" title='配置管理'\n" +
                "                        onclick=\"assigment(this)\">配置</a>\n" +
                "                    \n" +
                "                    <a class=\"collapse-item\" href=\"/securityIndex.html\">安全管理</a>\n" +
                "                    <a class=\"collapse-item\" href=\"/userLogMgmt.html\">日志管理</a>\n" +
                "                    <!--<a class=\"collapse-item\" href=\"/topoMgmt.html\">拓扑管理</a>-->\n" +
                "                    \n" +
                "                    <a class=\"collapse-item\" href=\"/softPackageMgmt.html\" title='软件管理'\n" +
                "                        onclick=\"assigment(this)\">软件管理</a>\n" +
                "                    \n" +
                "                    <a class=\"collapse-item\" href=\"/licenseMgmt.html\">许可管理</a>\n" +
                "                    <a class=\"collapse-item\" href=\"/updateWebInfo.html\">网站信息</a>\n" +
                "                    <a class=\"collapse-item\" href=\"/calltrace/callTrace.html\">信令跟踪</a>\n" +
                "                    \n" +
                "                    <a class=\"collapse-item\" href=\"/templateMgmt.html\">模板</a>\n" +
                "                    <!--<a class=\"collapse-item\" href='/ueInfo.html'>UE信息</a>-->\n" +
                "                    <a class=\"collapse-item\" href=\"/statusQueryIndex.html\">状态查询</a>\n" +
                "                    \n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </li>\n" +
                "\n" +
                "        \n" +
                "        <!-- Alarm -->\n" +
                "        <li class=\"nav-item\">\n" +
                "            <a role=\"button\" class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapseAlarm\"\n" +
                "                aria-expanded=\"false\" aria-controls=\"collapseAlarm\">\n" +
                "                <div class=\"top-menu-item\">\n" +
                "                    <div class=\"top-menu-item-icon\">\n" +
                "                        <!-- <i class=\"fa fa-exclamation-triangle\"></i> -->\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1588074627392\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"2393\" width=\"22\"\n" +
                "    height=\"22\" xmlns:xlink=\"http://www.w3.org/1999/xlink\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path\n" +
                "        d=\"M597.333333 119.466667c0 4.266667 8.533333 12.8 21.333334 34.133333s25.6 42.666667 42.666666 76.8 38.4 64 59.733334 102.4c21.333333 38.4 46.933333 76.8 68.266666 115.2 21.333333 38.4 46.933333 81.066667 68.266667 119.466667 21.333333 38.4 42.666667 76.8 64 106.666666 17.066667 34.133333 34.133333 59.733333 51.2 85.333334s21.333333 38.4 29.866667 46.933333c8.533333 17.066667 17.066667 34.133333 17.066666 51.2 4.266667 17.066667 0 29.866667-4.266666 46.933333s-12.8 25.6-21.333334 34.133334c-12.8 8.533333-25.6 12.8-42.666666 12.8L89.6 951.466667c-25.6 0-46.933333-4.266667-59.733333-12.8-12.8-8.533333-21.333333-21.333333-25.6-34.133334-4.266667-12.8-4.266667-29.866667 0-42.666666 4.266667-17.066667 8.533333-29.866667 21.333333-46.933334 4.266667-8.533333 12.8-21.333333 25.6-46.933333 12.8-21.333333 29.866667-51.2 51.2-81.066667 21.333333-34.133333 42.666667-68.266667 64-106.666666 21.333333-38.4 46.933333-81.066667 72.533333-119.466667s46.933333-81.066667 68.266667-119.466667c21.333333-38.4 42.666667-72.533333 59.733333-102.4 17.066667-29.866667 34.133333-55.466667 46.933334-76.8l21.333333-34.133333c8.533333-12.8 21.333333-25.6 38.4-34.133333 17.066667-8.533333 29.866667-12.8 46.933333-12.8s29.866667 4.266667 46.933334 8.533333c8.533333 4.266667 21.333333 17.066667 29.866666 29.866667zm-25.6 196.266666c0-8.533333 0-17.066667-4.266666-21.333333-4.266667-8.533333-8.533333-12.8-12.8-17.066667-4.266667-4.266667-12.8-8.533333-21.333334-12.8s-17.066667-8.533333-25.6-8.533333c-17.066667 0-29.866667 4.266667-42.666666 17.066667s-21.333333 25.6-21.333334 42.666666l0 264.533334c0 17.066667 8.533333 29.866667 21.333334 42.666666s25.6 17.066667 42.666666 17.066667 29.866667-4.266667 42.666667-17.066667c12.8-12.8 21.333333-25.6 21.333333-42.666666l0-264.533334zm-64 384c-17.066667 0-34.133333 4.266667-42.666666 17.066667s-17.066667 25.6-17.066667 42.666667 4.266667 34.133333 17.066667 42.666666c12.8 12.8 25.6 17.066667 42.666666 17.066667s34.133333-4.266667 42.666667-17.066667c12.8-12.8 17.066667-25.6 17.066667-42.666666s-4.266667-34.133333-17.066667-42.666667c-8.533333-12.8-25.6-17.066667-42.666667-17.066667z\"\n" +
                "        p-id=\"2394\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"top-menu-item-text\">\n" +
                "                        <span>告警</span>\n" +
                "                    </div>\n" +
                "                    <div class=\"right-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"down-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </a>\n" +
                "            <div id=\"collapseAlarm\" class=\"collapse\" aria-labelledby=\"headingAlarm\" data-parent=\"#accordion\">\n" +
                "                <div class=\"bg-gradient-tertiary py-2 collapse-inner rounded\">\n" +
                "                    <a class=\"collapse-item\" href=\"/activeAlarm.html\" title='实时告警'\n" +
                "                        onclick=\"assigment(this)\">实时告警</a>\n" +
                "                    <a class=\"collapse-item\" href=\"/historyAlarm.html\" title='历史告警'\n" +
                "                        onclick=\"assigment(this)\">历史告警</a>\n" +
                "                    <a class=\"collapse-item\" href=\"/alarmSummaryView.html\"\n" +
                "                        title='告警汇总视图'\n" +
                "                        onclick=\"assigment(this)\">告警汇总视图</a>\n" +
                "                    <a class=\"collapse-item\" href=\"/alarmFilePage.html\"\n" +
                "                        title='告警文件管理' onclick=\"assigment(this)\"\n" +
                "                        style=\"font-size:17px;\">告警文件管理</a>\n" +
                "                    <a class=\"collapse-item\" href=\"#\" data-target=\"#modalAdd\"\n" +
                "                        data-toggle=\"modal\">告警存储时间</a>\n" +
                "                    <a class=\"collapse-item\" href=\"#\" data-target=\"#modalAlarmTime\"\n" +
                "                        data-toggle=\"modal\">告警产生时间</a>\n" +
                "                    <a class=\"collapse-item\" href=\"#\" data-target=\"#modalCPU\"\n" +
                "                        data-toggle=\"modal\">CPU阈值设置</a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </li>\n" +
                "        <!-- Tools -->\n" +
                "        <li class=\"nav-item\">\n" +
                "            <a role=\"button\" class=\"nav-link collapsed\" href=\"#\" data-toggle=\"collapse\" data-target=\"#collapseGrafana\"\n" +
                "                aria-expanded=\"false\" aria-controls=\"collapseGrafana\">\n" +
                "                <div class=\"top-menu-item\">\n" +
                "                    <div class=\"top-menu-item-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
                "<svg t=\"1614252011000\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\"\n" +
                "     xmlns=\"http://www.w3.org/2000/svg\" p-id=\"4284\" width=\"22\"\n" +
                "    height=\"22\"\n" +
                "     xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs><style type=\"text/css\"></style></defs>\n" +
                "    <path d=\"M313.1 114.3h397.7c27.5 0 49.7 22.3 49.7 49.7H263.4c0-27.5 22.3-49.7 49.7-49.7zM164 213.7h696c54.9 0 99.4 44.5 99.4 99.4v99.4H64.6v-99.4c0-54.9 44.5-99.4 99.4-99.4z m298.3 397.7c0 27.5 22.3 49.7 49.7 49.7 27.5 0 49.7-22.3 49.7-49.7V462.3h397.8v348c0 54.9-44.5 99.4-99.4 99.4H164c-54.9 0-99.4-44.5-99.4-99.4v-348h397.7v149.1z\" p-id=\"4285\" fill=\"#ffffff\">\n" +
                "    </path>\n" +
                "</svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"top-menu-item-text\">\n" +
                "                        <span>集成</span>\n" +
                "                    </div>\n" +
                "                    <div class=\"right-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308106098\" class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"1066\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\"><defs><style type=\"text/css\"></style></defs><path d=\"M256 120.768L306.432 64 768 512l-461.568 448L256 903.232 659.072 512z\" p-id=\"1067\" fill=\"#ffffff\"></path></svg>\n" +
                "                    </div>\n" +
                "                    <div class=\"down-icon\">\n" +
                "                        <?xml version=\"1.0\" standalone=\"no\"?>\n" +
                "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\"><svg t=\"1574308096481\"\n" +
                "    class=\"icon\" viewBox=\"0 0 1024 1024\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\" p-id=\"909\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"22\" height=\"22\">\n" +
                "    <defs>\n" +
                "        <style type=\"text/css\"></style>\n" +
                "    </defs>\n" +
                "    <path d=\"M903.232 256l56.768 50.432L512 768 64 306.432 120.768 256 512 659.072z\" p-id=\"910\" fill=\"#ffffff\"></path>\n" +
                "</svg>\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "            </a>\n" +
                "            <div id=\"collapseGrafana\" class=\"collapse\"  data-parent=\"#accordion\">\n" +
                "                <div class=\"bg-gradient-tertiary py-2 collapse-inner rounded\">\n" +
                "                    \n" +
                "                    <a class=\"collapse-item\" href=\"http://172.25.11.12:3000/d/13VRwsHnz/basic-info?orgId=1\">Grafana</a>\n" +
                "                    \n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </li>\n" +
                "        \n" +
                "\n" +
                "        <!-- Sidebar Toggler (Sidebar) -->\n" +
                "        <div class=\"text-center d-none d-md-inline\">\n" +
                "            <button class=\"rounded-circle border-0\" id=\"sidebarToggle\">\n" +
                "                <div class=\"normal-arrow\">\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"30\" height=\"30\">\n" +
                "                        <polyline points=\"17,8 10,15 17,22\" class=\"arrow-logo\" />\n" +
                "                    </svg>\n" +
                "                </div>\n" +
                "                <div class=\"toggle-arrow\">\n" +
                "                    <svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"30\" height=\"30\">\n" +
                "                        <polyline points=\"13,8 20,15 13,22\" class=\"arrow-logo\" />\n" +
                "                    </svg>\n" +
                "                </div>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "    </ul>\n" +
                "    <!--End of Sidebar -->\n" +
                "    <input type=\"hidden\" name=\"user-type\" value=\"\" />\n" +
                "    <!-- Topbar -->\n" +
                "    <nav class=\"navbar navbar-expand navbar-light  topbar mb-4 static-top shadow  navbar-fixed-top\">\n" +
                "        <!-- Sidebar Toggle (Topbar) -->\n" +
                "        <div class=\"btn-sidebar-toggle-top\">\n" +
                "            <button id=\"sidebarToggleTop\" class=\"btn btn-link d-md-none rounded-circle mr-3\">\n" +
                "                <i class=\"glyphicon glyphicon-menu-hamburger fa-2x\"></i>\n" +
                "            </button>\n" +
                "        </div>\n" +
                "        <!--Topbar Right -->\n" +
                "        <div class=\"topTab\" id=\"languageTab\" >\n" +
                "            <div class=\"nav-link\">\n" +
                "                <form id=\"languageForm\" method=\"post\">\n" +
                "                    <input type='hidden' name='csrfmiddlewaretoken' value='Iz6z8kjJ07RxgluxLjk3yBAHrlZSw62vcEnByZv7iMXIk9YKLI0KC3przU1PHewX' />\n" +
                "                    <select class=\"form-control\" id=\"language\" name=\"language\" onchange=\"languageCb(this)\">\n" +
                "                        \n" +
                "                        \n" +
                "                        <option value=\"en\">English</option>\n" +
                "                        \n" +
                "                        \n" +
                "                        \n" +
                "                        <option value=\"zh-hans\" selected=\"selected\">简体中文</option>\n" +
                "                        \n" +
                "                        \n" +
                "                    </select>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "        <div class=\"topTab\">\n" +
                "            <a class=\"nav-link\" href=\"#\" id=\"logout\" role=\"button\" title='登出'>\n" +
                "                <i class=\"glyphicon glyphicon-off\"></i><span>登出</span>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <div class=\"topTab\">\n" +
                "            <a class=\"nav-link\" id=\"userlink\" role=\"button\" onclick=\"showChangePasswordModal()\">\n" +
                "                <i class=\"glyphicon glyphicon-user \"></i><span id=\"User\">admin</span>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "        <div class=\"topTab\">\n" +
                "            <a class=\"nav-link\" href=\"/activeAlarm.html\" id=\"alarmNumLi\" role=\"button\"\n" +
                "                title='告警'>\n" +
                "                <span class=\"glyphicon glyphicon-bell\"></span>\n" +
                "                <span id=\"alarmNum\" class=\"badge bg-danger popover-toggle\" title=\"<h4>Alarm Arrives</h4>\"\n" +
                "                    data-container=\"body\" data-toggle=\"popover\" data-placement=\"left\" data-content=\"\"></span>\n" +
                "            </a>\n" +
                "        </div>\n" +
                "    </nav>\n" +
                "    <!-- End of Topbar -->\n" +
                "    <div class=\"main\">\n" +
                "        <div class=\"main-padding-top\"></div>\n" +
                "        <div id=\"breadcrumb\">\n" +
                "            \n" +
                "<nav aria-label=\"breadcrumb\">\n" +
                "    <ol class=\"breadcrumb\">\n" +
                "        <li class=\"breadcrumb-item\">系统</li>\n" +
                "        <li class=\"breadcrumb-item\"><a href=\"/ueInfo.html\">UE信息</a></li>\n" +
                "    </ol>\n" +
                "</nav>\n" +
                "\n" +
                "        </div>\n" +
                "        <div id=\"mainContent\" class=\"table-form\">\n" +
                "            \n" +
                "<div class=\"content-container panelGap\">\n" +
                "    <div class=\"form-group text-left\">\n" +
                "        <!--<form id=\"queryForm\" method=\"get\" action=\"/ueInfo.html\">-->\n" +
                "        <label>IMSI</label>\n" +
                "        <input type=\"text\" class=\"form-control\" name=\"imsi\" id=\"imsi\" maxlength=\"15\" placeholder=\"example: 46000*\" required>\n" +
                "        <label>RAN IP</label>\n" +
                "        <input type=\"text\" name=\"ipaddress\" id=\"ipaddress\" class=\"form-control\">\n" +
                "        <label>UE状态</label>\n" +
                "        <select class=\"form-control\" name=\"userStateInfo\" id=\"userStateInfo\" multiple=\"multiple\">\n" +
                "            <option value=\"CONNECTED\">CONNECTED</option>\n" +
                "            <option value=\"IDLE\">IDLE</option>\n" +
                "            <option value=\"DEREGISTERED\">DEREGISTERED</option>\n" +
                "            <option value=\"UNKNOWN\">UNKNOWN</option>\n" +
                "        </select>\n" +
                "        <button type=\"button\" class=\"btn btn-default btn-primary\" id=\"queryBtn\" onclick=\"query()\">\n" +
                "            <span class=\"glyphicon glyphicon-search\"></span> 查询\n" +
                "        </button>\n" +
                "        <button type=\"button\" class=\"btn btn-default btn-primary\" id=\"downloadBtn\" onclick=\"sendDownloadRequest()\">\n" +
                "            <span class=\"glyphicon glyphicon-download\"></span> 下载\n" +
                "        </button>\n" +
                "        <div></div>\n" +
                "        <!--</form>-->\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "<div class=\"content-container panelGap\">\n" +
                "    共: 8,&nbsp;\n" +
                "    连接: 8,&nbsp;\n" +
                "    待机: 0\n" +
                "    \n" +
                "    \n" +
                "</div>\n" +
                "\n" +
                "<div class=\"content-container panelGap\">\n" +
                "    <div class=\"table-responsive\">\n" +
                "        <table class=\"table table-bordered table-hover\">\n" +
                "            <thead class=\"table-header\">\n" +
                "            <tr>\n" +
                "                <th>IMSI</th>\n" +
                "                <th>UE IP</th>\n" +
                "                <th>UE状态</th>\n" +
                "                <th>注册时间</th>\n" +
                "                <th>RAN IP</th>\n" +
                "                <th>RAN类型</th>\n" +
                "                <th>Cell ID</th>\n" +
                "            </tr>\n" +
                "            </thead>\n" +
                "            \n" +
                "            \n" +
                "            <tbody>\n" +
                "\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000004901</td>\n" +
                "                <td>172.20.80.1, 20.0.3.67</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 11:10:09</td>\n" +
                "                <td>172.25.11.106</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de3a</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000004909</td>\n" +
                "                <td>172.20.80.9</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 11:08:27</td>\n" +
                "                <td>172.25.11.137</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>008d259</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000004924</td>\n" +
                "                <td>172.20.80.24, 20.0.3.131</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 11:29:32</td>\n" +
                "                <td>172.25.11.115</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de43</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000004940</td>\n" +
                "                <td>172.20.80.40, 20.0.3.3</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 11:09:38</td>\n" +
                "                <td>172.25.11.114</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de42</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000005021</td>\n" +
                "                <td>172.20.80.121</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-06 14:43:03</td>\n" +
                "                <td>172.25.11.126</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de4e</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000005022</td>\n" +
                "                <td>172.20.80.122</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 14:32:14</td>\n" +
                "                <td>172.25.11.106</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de3a</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000005023</td>\n" +
                "                <td>172.20.80.123</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 09:14:50</td>\n" +
                "                <td>172.25.11.114</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de42</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "            <tr>\n" +
                "                <td>460060000005024</td>\n" +
                "                <td>172.20.80.124</td>\n" +
                "                <td>CONNECTED</td>\n" +
                "                <td>2022-06-07 14:32:00</td>\n" +
                "                <td>172.25.11.131</td>\n" +
                "                <td>HomeEnbId</td>\n" +
                "                <td>cb8de53</td>\n" +
                "            </tr>\n" +
                "            \n" +
                "\n" +
                "            \n" +
                "            </tbody>\n" +
                "\n" +
                "            \n" +
                "\n" +
                "            \n" +
                "\n" +
                "            \n" +
                "        </table>\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <!-- Footer -->\n" +
                "    <nav class=\"navbar navbar-default navbar-fixed-bottom\">\n" +
                "        <div class=\"center-block footer\">\n" +
                "            <span>IPLOOK</span>\n" +
                "        </div>\n" +
                "    </nav>\n" +
                "\n" +
                "    <!-- Hidden field -->\n" +
                "    <input type=\"hidden\" id=\"userStatus\" value=\"\" />\n" +
                "\n" +
                " <script type=\"text/javascript\" src=\"/jsi18n/\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/jquery-2.1.4.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/jquery-1.10.2/jquery.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/jquery-ui-1.10.4/jquery-ui-1.10.4.custom.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/jquery-form/jquery.form.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/jquery.cookie.js\" charset=\"utf-8\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/unity5G/defines.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/jsencrypt.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/calendar.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/bootstrap.bundle.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/bootstrap-multiselect.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/sb-admin-2.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/bootstrap-switch.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/bootstrap-datetimepicker.min.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/bootstrap-datetimepicker.zh-CN.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/modal.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/common.js\"></script>\n" +
                "    <script type=\"text/javascript\" src=\"/static/js/unity5G/main.js\"></script>\n" +
                "    <script src=\"/static/js/formating.js\"></script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        $(\".popover-toggle\").popover({ html: true, trigger: \"manual\" });\n" +
                "    </script>\n" +
                "    <script type=\"text/javascript\">\n" +
                "        $(document).ready(function () {\n" +
                "            var userName = $.cookie(\"login_userName\");\n" +
                "            var userType = \"\";\n" +
                "            var serverIp = getServerIP();\n" +
                "            if (!serverIp && location.href.indexOf(\"systemMgmt.html\") > 0) {\n" +
                "            }\n" +
                "            else if (userName == null || userName == \"LOG_OUT\") {\n" +
                "                location.href = \"index.html\";\n" +
                "            }\n" +
                "            else {\n" +
                "                userType = $.cookie(\"login_userType\");\n" +
                "                deviceType = $.cookie(\"deviceType\");\n" +
                "                getDetailHtml(userName, userType, deviceType);\n" +
                "            }\n" +
                "        });\n" +
                "        document.onkeypress = function (e) {\n" +
                "            var code;\n" +
                "            if (!e) {\n" +
                "                var e = window.event;\n" +
                "            }\n" +
                "            if (e.keyCode) {\n" +
                "                code = e.keyCode;\n" +
                "            }\n" +
                "            else if (e.which) {\n" +
                "                code = e.which\n" +
                "            }\n" +
                "            if (code == 13) {\n" +
                "                return false;\n" +
                "            }\n" +
                "        };\n" +
                "    </script>\n" +
                "    \n" +
                "<script src=\"/static/js/unity5G/ueInfo.js\"></script>\n" +
                "\n" +
                "\n" +
                "    <!-- 告警保存期限模态框 -->\n" +
                "    <div class=\"modal fade\" id=\"modalAdd\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "        <div class=\"modal-dialog modal-sm\">\n" +
                "            <div class=\"modal-content\">\n" +
                "                <form id=\"alarmStorageTimeForm\" method=\"post\" action=\"/alarmAndCPU\">\n" +
                "                    <input type='hidden' name='csrfmiddlewaretoken' value='Iz6z8kjJ07RxgluxLjk3yBAHrlZSw62vcEnByZv7iMXIk9YKLI0KC3przU1PHewX' />\n" +
                "                    <div class=\"modal-header\">\n" +
                "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "                        <h3 class=\"modal-title text-center\">告警存储时间设置</h3>\n" +
                "                    </div>\n" +
                "                    <div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "                        <table>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>选择时间</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td><select name=\"alarmStorageTime\" class=\"form-control\" id=\"alarmStorageTime\"\n" +
                "                                        onchange=\"alarmStorageTimeSelectCb(this)\">\n" +
                "                                        <option value=\" 3\">三天</option>\n" +
                "                                        <option value=\"15\">15天</option>\n" +
                "                                        <option value=\"30\">30天</option>\n" +
                "                                        <option value=\"other\">其他</option>\n" +
                "                                    </select>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td class=\"timeValue\"><input type=\"text\" class=\"form-control number\" id=\"timeNum\"\n" +
                "                                        name=\"timeNum\" oninput=\"timeNumCb(this)\"></td>\n" +
                "                                <td class=\"current-day\">天</td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div class=\"modal-footer\">\n" +
                "                        <button type=\"button\" class=\"btn btn-primary\"\n" +
                "                            onclick=\"alarmStorageTimeCb()\">保存</button>\n" +
                "                        <button type=\"button\" class=\"btn btn-default\" data-dismiss=\"modal\">取消</button>\n" +
                "                        <input type=\"hidden\" id=\"actionType\" name=\"actionType\" value=\"addNode\">\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- 告警产生时间模态框 -->\n" +
                "    <div class=\"modal fade\" id=\"modalAlarmTime\" tabindex=\"-1\" role=\"dialog\" style=\"z-index:1042;\"\n" +
                "        aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "        <div class=\"modal-dialog modal-sm\">\n" +
                "            <div class=\"modal-content\">\n" +
                "                <form id=\"alarmTimeForm\" method=\"post\" action=\"/alarmAndCPU\">\n" +
                "                    <input type='hidden' name='csrfmiddlewaretoken' value='Iz6z8kjJ07RxgluxLjk3yBAHrlZSw62vcEnByZv7iMXIk9YKLI0KC3przU1PHewX' />\n" +
                "                    <div class=\"modal-header\">\n" +
                "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "                        <h3 class=\"modal-title text-center\">告警产生时间设置</h3>\n" +
                "                    </div>\n" +
                "                    <div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "                        <table>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>设置告警时间</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td>\n" +
                "                                    <input style=\"width:50%;\" oninput=\"setAlarmTime()\" type=\"text\" name=\"alarmTimeNUm\"\n" +
                "                                        class=\"form-control number\" id=\"alarmTimeNUm\" value=\"\">\n" +
                "                                    <select style=\"width:40%;\" name=\"alarmUnit\" class=\"form-control\" id=\"alarmUnit\">\n" +
                "                                        <option value=\"hour\">小时</option>\n" +
                "                                        <option value=\"day\">天</option>\n" +
                "                                        <option value=\"month\">月</option>\n" +
                "                                    </select>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div class=\"modal-footer\">\n" +
                "                        <button type=\"button\" class=\"btn btn-warning\"\n" +
                "                            onclick=\"getAlarmTimeDuration()\">获取</button>\n" +
                "                        <button type=\"button\" class=\"btn btn-primary\"\n" +
                "                            onclick=\"setAlarmTimeDuration()\">保存</button>\n" +
                "                        <input type=\"hidden\" id=\"actionType\" name=\"actionType\" value=\"addNode\">\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "    <!-- CPU阈值告警模态框 -->\n" +
                "    <div class=\"modal fade\" id=\"modalCPU\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "        <div class=\"modal-dialog modal-sm\">\n" +
                "            <div class=\"modal-content\">\n" +
                "                <form id=\"cpuLimitSetForm\" method=\"post\" action=\"/alarmAndCPU\">\n" +
                "                    <input type='hidden' name='csrfmiddlewaretoken' value='Iz6z8kjJ07RxgluxLjk3yBAHrlZSw62vcEnByZv7iMXIk9YKLI0KC3przU1PHewX' />\n" +
                "                    <div class=\"modal-header\">\n" +
                "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "                        <h3 class=\"modal-title text-center\">CPU阈值设置</h3>\n" +
                "                    </div>\n" +
                "                    <div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "                        <table>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>NF 类型</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td>\n" +
                "                                    <select name=\"nfType\" class=\"form-control\" id=\"nfTypes\"\n" +
                "                                        onchange=\"nfTypeSelect(this)\">\n" +
                "                                        \n" +
                "                                        <option value=\"GW\">GW</option>\n" +
                "                                        \n" +
                "                                        <option value=\"MME\">MME</option>\n" +
                "                                        \n" +
                "                                        <option value=\"SCEF\">SCEF</option>\n" +
                "                                        \n" +
                "                                        <option value=\"UDM\">UDM</option>\n" +
                "                                        \n" +
                "                                    </select>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>NF名称</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td>\n" +
                "                                    <select name=\"nfId\" class=\"form-control\" id=\"nfIds\">\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <option value=\"gw01\" class=\"gw\">gw01</option>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <option value=\"mme01\" class=\"mme\">mme01</option>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <option value=\"nef01\" class=\"scef\">nef01</option>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        \n" +
                "                                        <option value=\"hss01\" class=\"udm\">hss01</option>\n" +
                "                                        \n" +
                "                                        \n" +
                "                                    </select>\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>告警产生阈值</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td class=\"percentage\"> <input type=\"text\" placeholder=\"0~100\"\n" +
                "                                        class=\"form-control number\" id=\"alarmProPercentage\" name=\"alarmProPercentage\" maxlength=\"3\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>告警清除阈值</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td class=\"percentage\">\n" +
                "                                    <input type=\"text\" placeholder=\"0~100\" class=\"form-control number\" maxlength=\"3\"\n" +
                "                                        id=\"alarmClearPercentage\" name=\"alarmClearPercentage\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div class=\"modal-footer\">\n" +
                "                        <button type=\"button\" class=\"btn btn-warning\" onclick=\"getCPULimit()\">获取</button>\n" +
                "                        <button type=\"button\" class=\"btn btn-primary\"\n" +
                "                            onclick=\"cpuLimitSet()\">保存</button>\n" +
                "                        <input type=\"hidden\" id=\"actionType\" name=\"actionType\" value=\"addNode\">\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"modal fade\" id=\"modalPassword\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\"\n" +
                "        aria-hidden=\"true\">\n" +
                "        <div class=\"modal-dialog modal-sm\">\n" +
                "            <div class=\"modal-content\">\n" +
                "                <form id=\"passwordForm\" method=\"post\" action=\"/securityMgmt.html\">\n" +
                "                    <input type='hidden' name='csrfmiddlewaretoken' value='Iz6z8kjJ07RxgluxLjk3yBAHrlZSw62vcEnByZv7iMXIk9YKLI0KC3przU1PHewX' />\n" +
                "                    <div class=\"modal-header\">\n" +
                "                        <button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "                        <h3 class=\"modal-title text-center\">修改密码</h3>\n" +
                "                    </div>\n" +
                "                    <div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "                        <table>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>旧密码</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td>\n" +
                "                                    <input type=\"password\" class=\"form-control\" id=\"oldPwd\" name=\"oldPwd\" max=\"128\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>新密码</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td>\n" +
                "                                    <input type=\"password\" class=\"form-control\" id=\"newPwd\" name=\"newPwd\" max=\"128\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-title\">\n" +
                "                                <td><label>确认密码</label></td>\n" +
                "                            </tr>\n" +
                "                            <tr class=\"row-content\">\n" +
                "                                <td>\n" +
                "                                    <input type=\"password\" class=\"form-control\" id=\"confirmPwd\" name=\"confirmPwd\"\n" +
                "                                        max=\"128\">\n" +
                "                                </td>\n" +
                "                            </tr>\n" +
                "                        </table>\n" +
                "                    </div>\n" +
                "                    <div class=\"modal-footer\">\n" +
                "                        <button type=\"button\" class=\"btn btn-primary\"\n" +
                "                            onclick=\"savePassword()\">保存</button>\n" +
                "                    </div>\n" +
                "                </form>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "\n" +
                "    \n" +
                "    <div class=\"modal modal-fail fade\" id=\"modalFail\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "\t\t<div class=\"modal-dialog modal-sm\">\n" +
                "\t\t\t<div class=\"modal-content\">\n" +
                "\t\t\t\t<div class=\"modal-header\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "\t\t\t\t\t<h3 class=\"modal-title text-center\">失败</h3>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"modal-footer\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\">关闭</button>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "    \n" +
                "    <div class=\"modal fade\" id=\"modalSuccess\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "\t\t<div class=\"modal-dialog modal-sm\">\n" +
                "\t\t\t<div class=\"modal-content\"> \n" +
                "\t\t\t\t<div class=\"modal-header\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "\t\t\t\t\t<h3 class=\"modal-title text-center\">成功</h3>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div id=\"dialogInfo\" class=\"modal-body model-successful\">\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"modal-footer\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\">关闭</button>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "    \n" +
                "    <div class=\"modal modal-warning fade\" id=\"modalWarning\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "\t\t<div class=\"modal-dialog modal-sm\">\n" +
                "\t\t\t<div class=\"modal-content\">\n" +
                "\t\t\t\t<div class=\"modal-header\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "\t\t\t\t\t<h3 class=\"modal-title text-center\">警告</h3>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"modal-footer\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"btn btn-primary\" data-dismiss=\"modal\">关闭</button>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "    \n" +
                "    <!--https://blog.csdn.net/davidhhs/article/details/80974425-->\n" +
                "    <div class=\"modal fade\" id=\"modalConfirm\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"myModalLabel\" aria-hidden=\"true\">\n" +
                "\t\t<div class=\"modal-dialog modal-sm\">\n" +
                "\t\t\t<div class=\"modal-content\">\n" +
                "\t\t\t\t<div class=\"modal-header\">\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-hidden=\"true\">×</button>\n" +
                "\t\t\t\t\t<h3 class=\"modal-title text-center\">[Title]</h3>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div id=\"dialogInfo\" class=\"modal-body\">\n" +
                "                    <p>[Message]</p>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t\t<div class=\"modal-footer\">\n" +
                "                    <button type=\"button\" class=\"btn btn-primary ok\" data-dismiss=\"modal\">[BtnOk]</button>\n" +
                "\t\t\t\t\t<button type=\"button\" class=\"btn btn-default cancel\" data-dismiss=\"modal\">[BtnCancel]</button>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";
        if (StringUtils.isNotEmpty(htmlContent)) {
            Document doc = Jsoup.parse(htmlContent);
            Elements rows = doc.select("table[class=table table-bordered table-hover]").get(0).select("tr");
            if (Objects.nonNull(rows) && rows.size() > 1){
                for (int i = 1; i < rows.size(); i++) {
                    Element row = rows.get(i);
                    Elements tdElements = row.select("td");
                    if(Objects.nonNull(tdElements) && tdElements.size() > 0){
                        String imsi = tdElements.get(0).text();
                        String ueIp = tdElements.get(1).text();
                        String status = tdElements.get(2).text();
                        String eNodeBIP = tdElements.get(4).text();
                        String testData = tdElements.get(5).text();
                    }
                }
            }
        }

        String newCoreUrl1 = "http://%s/ueInfo.html?query=true&filter={\"userStateInfo.5gsUserState\":\"{\\\"$in\\\":[\\\"CONNECTED\\\",\\\"IDLE\\\"]}\",\"imsi\":\"{\\\"$regex\\\":\\\"^\\\"}\"}";
        Map<String, String> item1 = new HashMap<>();
        item1.put("$regex","^");
        Map<String, List<String>> item2 = new HashMap<>();
        item2.put("$in", Lists.newArrayList("CONNECTED","IDLE"));
        Map<String, String> data = new HashMap<>();
        data.put("imsi", JSONObject.toJSONString(item1));
        data.put("userStateInfo.5gsUserState", JSONObject.toJSONString(item2));
        String filter = JSONObject.toJSONString(data);

        System.out.println(String.format(newCoreUrl1, "127.0.0.1"));
    }
}
