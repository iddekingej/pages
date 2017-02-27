package org.elaya.page.view;

import java.io.IOException;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.DataException;
import org.elaya.page.core.KeyNotFoundException;
import org.elaya.page.core.PageSession;
import org.elaya.page.data.MapData;
import org.elaya.page.data.XMLBaseDataItem.XMLDataException;
import org.elaya.page.formula.FormulaException;
import org.elaya.page.widget.Element.DisplayException;
import org.elaya.page.xml.XMLParserBase.XMLLoadException;
import org.xml.sax.SAXException;

@FunctionalInterface
public interface AbstractView {
	public void render(MapData mapData, PageSession pageSession) throws IOException, DisplayException, SQLException, DefaultDBConnectionNotSet, KeyNotFoundException, ParserConfigurationException, SAXException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, XMLLoadException,  DataException, ClassNotFoundException, FormulaException, XMLDataException ;
}
