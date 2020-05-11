package controller;

import exceptionalMassage.ExceptionalMassage;
import product.Category;
import product.Product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class FilterAndSort {
    private boolean availabilityFilter;
    private Integer priceLowerBound;
    private Integer priceUpperBound;
    private SortType sortType;
    private Category category;
    private final HashMap<String, ArrayList<String>> specialFilter;
    private final ArrayList<String> nameFilter;
    private final ArrayList<String> brandFilter;

    public FilterAndSort() {
        this.availabilityFilter = false;
        this.priceLowerBound = null;
        this.priceUpperBound = null;
        this.sortType = SortType.BY_NUMBER_OF_VIEWS;
        this.category = Category.superCategory;
        this.specialFilter = new HashMap<>();
        this.nameFilter = new ArrayList<>();
        this.brandFilter = new ArrayList<>();
    }

    public void clear() {
        this.availabilityFilter = false;
        this.priceLowerBound = null;
        this.priceUpperBound = null;
        this.sortType = SortType.BY_NUMBER_OF_VIEWS;
        this.category = Category.superCategory;
        this.specialFilter.clear();
        this.nameFilter.clear();
        this.brandFilter.clear();
    }

    public void clearFiltersExceptCategory() {
        this.availabilityFilter = false;
        this.priceLowerBound = null;
        this.priceUpperBound = null;
        this.sortType = SortType.BY_NUMBER_OF_VIEWS;
        this.specialFilter.clear();
        this.nameFilter.clear();
        this.brandFilter.clear();
    }

    public boolean getAvailabilityFilter() {
        return availabilityFilter;
    }

    public Integer getPriceLowerBound() {
        return priceLowerBound;
    }

    public Integer getPriceUpperBound() {
        return priceUpperBound;
    }

    public SortType getSortType() {
        return sortType;
    }

    public Category getCategory() {
        return category;
    }

    public HashMap<String, ArrayList<String>> getSpecialFilter() {
        return specialFilter;
    }

    public ArrayList<String> getNameFilter() {
        return nameFilter;
    }

    public ArrayList<String> getBrandFilter() {
        return brandFilter;
    }

    public void setAvailabilityFilter(boolean availabilityFilter) {
        this.availabilityFilter = availabilityFilter;
    }

    public void setPriceLowerBound(Integer priceLowerBound) throws ExceptionalMassage {
        //null and 0 for no filter
        if (priceLowerBound == null)
            this.priceLowerBound = null;
        else {
            if (!priceLowerBound.equals(0) && !availabilityFilter)
                throw new ExceptionalMassage("You must apply availability filter.");
            if (priceLowerBound.equals(0))
                this.priceLowerBound = null;
            else
                this.priceLowerBound = priceUpperBound;
        }
    }

    public void setPriceUpperBound(Integer priceUpperBound) throws ExceptionalMassage {
        //null and 0 for no filter
        if (priceUpperBound == null)
            this.priceUpperBound = null;
        else {
            if (!priceUpperBound.equals(0) && !availabilityFilter)
                throw new ExceptionalMassage("You must apply availability filter.");
            if (priceUpperBound.equals(0))
                this.priceUpperBound = null;
            else
                this.priceUpperBound = priceUpperBound;
        }
    }

    public void setSortType(SortType sortType) {
        this.sortType = sortType;
    }

    public void setCategory(Category category) {
        //null for no filter
        if (category == null)
            this.category = Category.superCategory;
        else
            this.category = category;
        clearFiltersExceptCategory();
    }

    private ArrayList<Product> applyAvailabilityFilter(ArrayList<Product> products) {
        ArrayList<Product> filteredProducts;
        if (availabilityFilter) {
            filteredProducts = new ArrayList<>();
            for (Product product : products)
                if (product.isProductAvailableNow())
                    filteredProducts.add(product);
        } else {
            filteredProducts = new ArrayList<>(products);
        }
        return filteredProducts;
    }

    private ArrayList<Product> applyPriceLowerBound(ArrayList<Product> products) {
        ArrayList<Product> filteredProducts;
        if (priceLowerBound != null) {
            filteredProducts = new ArrayList<>();
            for (Product product : products)
                if (product.isProductProvidedInPriceUpperThan(priceLowerBound))
                    filteredProducts.add(product);
        } else {
            filteredProducts = new ArrayList<>(products);
        }
        return filteredProducts;
    }

    private ArrayList<Product> applyPriceUpperBound(ArrayList<Product> products) {
        ArrayList<Product> filteredProducts;
        if (priceUpperBound != null) {
            filteredProducts = new ArrayList<>();
            for (Product product : products)
                if (product.isProductProvidedInPriceLowerThan(priceUpperBound))
                    filteredProducts.add(product);
        } else {
            filteredProducts = new ArrayList<>(products);
        }
        return filteredProducts;
    }

    private ArrayList<Product> sort(ArrayList<Product> products) {
        products = new ArrayList<>(products);
        Comparator<Product> sortComparator = sortType.getComparator();
        products.sort(sortComparator);
        return products;
    }

    public HashMap<String, ArrayList<String>> getAvailableSpecialFilters() {
        return category.getAvailableSpecialFilters();
    }

    public void addSpecialFilter(String key, String value) throws ExceptionalMassage {
        HashMap<String, ArrayList<String>> availableSpecialFilters = getAvailableSpecialFilters();
        if (!availableSpecialFilters.containsKey(key))
            throw new ExceptionalMassage("Filter with this key is not available");
        if (!availableSpecialFilters.get(key).contains(value))
            throw new ExceptionalMassage("Filter with this value is not available in this key.");
        if (specialFilter.containsKey(key)) {
            specialFilter.get(key).add(value);
        } else {
            ArrayList<String> keyValuesTemp = new ArrayList<>();
            keyValuesTemp.add(value);
            specialFilter.put(key, keyValuesTemp);
        }
    }

    public void removeSpecialFilter(String key, String value) throws ExceptionalMassage {
        if (!specialFilter.containsKey(key))
            throw new ExceptionalMassage("No filter with this key has applied.");
        ArrayList<String> keyValues = specialFilter.get(key);
        if (!keyValues.contains(value))
            throw new ExceptionalMassage("No filter with this value on this key has applied.");
        keyValues.remove(value);
        if (keyValues.size() == 0)
            specialFilter.remove(key);
    }

    private ArrayList<Product> applySpecialFilter(ArrayList<Product> products) {
        products = new ArrayList<>(products);
        if (specialFilter.size() != 0) {
            for (String key : specialFilter.keySet()){
                ArrayList<Product> keyFilteredProduct = new ArrayList<>();
                for (Product product : products) {
                    if (specialFilter.get(key).contains(product.getSpecification().get(key)))
                        keyFilteredProduct.add(product);
                }
                products = keyFilteredProduct;
            }
        }
        return products;
    }

    public void addNameFilter(String name) throws ExceptionalMassage {
        if (nameFilter.contains(name))
            throw new ExceptionalMassage("This name filter has already applied.");
        nameFilter.add(name);
    }

    public void removeNameFilter(String name) throws ExceptionalMassage {
        if (!nameFilter.contains(name))
            throw new ExceptionalMassage("This name filter hasn't applied yet.");
        nameFilter.remove(name);
    }

    private ArrayList<Product> applyNameFilter(ArrayList<Product> products) {
        if (nameFilter.size() == 0) {
            return new ArrayList<>(products);
        } else {
            ArrayList<Product> filteredProducts = new ArrayList<>();
            for (Product product : products){
                if (nameFilter.contains(product.getName()))
                    filteredProducts.add(product);
            }
            return filteredProducts;
        }
    }

    public void addBrandFilter(String brand) throws ExceptionalMassage {
        if (brandFilter.contains(brand))
            throw new ExceptionalMassage("This brand filter has already applied.");
        brandFilter.add(brand);
    }

    public void removeBrandFilter(String brand) throws ExceptionalMassage {
        if (!brandFilter.contains(brand))
            throw new ExceptionalMassage("This brand filter hasn't applied yet.");
        brandFilter.remove(brand);
    }

    private ArrayList<Product> applyBrandFilter(ArrayList<Product> products) {
        if (brandFilter.size() == 0) {
            return new ArrayList<>(products);
        } else {
            ArrayList<Product> filteredProducts = new ArrayList<>();
            for (Product product : products){
                if (brandFilter.contains(product.getNameOfCompany()))
                    filteredProducts.add(product);
            }
            return filteredProducts;
        }
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = category.getAllProductInAllSubCategories();
        products = applyAvailabilityFilter(products);
        products = applyPriceLowerBound(products);
        products = applyPriceUpperBound(products);
        products = applySpecialFilter(products);
        products = applyNameFilter(products);
        products = applyBrandFilter(products);
        products = sort(products);
        return products;
    }
}

