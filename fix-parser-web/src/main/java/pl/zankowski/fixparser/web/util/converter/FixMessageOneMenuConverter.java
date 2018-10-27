package pl.zankowski.fixparser.web.util.converter;

import com.google.inject.Inject;
import com.google.inject.Injector;
import pl.zankowski.fixparser.messages.api.FixMessageTO;
import pl.zankowski.fixparser.messages.api.dictionary.DictionaryDescriptorTO;
import pl.zankowski.fixparser.messages.spi.MessageService;
import pl.zankowski.fixparser.user.api.Permission;
import pl.zankowski.fixparser.web.util.FacesUtils;
import pl.zankowski.fixparser.web.util.ShiroUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.servlet.ServletContext;

@FacesConverter(value = "fixMessageConverter", forClass = Object.class)
public class FixMessageOneMenuConverter implements Converter {

    private static final char DELIMITER = ';';
    private static final char ENTRY_DELIMITER = '#';
    private final FixMessageConverter messageConverter = new FixMessageConverter();

    private FacesUtils facesUtils;
    private ShiroUtils shiroUtils;
    private MessageService parserService;

    public FixMessageOneMenuConverter() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance()
                .getExternalContext().getContext();
        Injector injector = (Injector) servletContext.getAttribute(Injector.class.getName());
        injector.injectMembers(this);
    }

    @Inject
    public void setFacesUtils(FacesUtils facesUtils) {
        this.facesUtils = facesUtils;
    }

    @Inject
    public void setShiroUtils(ShiroUtils shiroUtils) {
        this.shiroUtils = shiroUtils;
    }

    @Inject
    public void setParserService(MessageService parserService) {
        this.parserService = parserService;
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        int index = value.indexOf(DELIMITER);

        DictionaryDescriptorTO providerDescriptor = DefaultFixDefinitionProvider.DESCRIPTOR;
        if (shiroUtils.isUserAuthenticated()) {
            providerDescriptor = (ProviderDescriptor) facesUtils.getContextAttribute(
                    shiroUtils.getCurrentUserID().getId() + FixDefinitionProvider.class.getSimpleName());
        }

        return messageConverter.convertToFixMessage(
                value.substring(index + 1),
                String.valueOf(ENTRY_DELIMITER),
                Integer.parseInt(value.substring(0, index)),
                parserService.getDefinitionProvider(
                        providerDescriptor,
                        shiroUtils.getCurrentUserID(),
                        shiroUtils.isPermitted(Permission.PRO.name()) || shiroUtils.isPermitted(Permission.ENTERPRISE.name())
                ));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return "";
        } else {
            FixMessageTO fixMessage = (FixMessageTO) value;
            return fixMessage.getMessageID().toString() + DELIMITER + messageConverter.convertToString(fixMessage, ENTRY_DELIMITER);
        }
    }

}